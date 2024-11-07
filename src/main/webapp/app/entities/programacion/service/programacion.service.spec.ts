import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IProgramacion } from '../programacion.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../programacion.test-samples';

import { ProgramacionService, RestProgramacion } from './programacion.service';

const requireRestSample: RestProgramacion = {
  ...sampleWithRequiredData,
  fecha: sampleWithRequiredData.fecha?.format(DATE_FORMAT),
  desde: sampleWithRequiredData.desde?.toJSON(),
  hasta: sampleWithRequiredData.hasta?.toJSON(),
};

describe('Programacion Service', () => {
  let service: ProgramacionService;
  let httpMock: HttpTestingController;
  let expectedResult: IProgramacion | IProgramacion[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ProgramacionService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Programacion', () => {
      const programacion = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(programacion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Programacion', () => {
      const programacion = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(programacion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Programacion', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Programacion', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Programacion', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProgramacionToCollectionIfMissing', () => {
      it('should add a Programacion to an empty array', () => {
        const programacion: IProgramacion = sampleWithRequiredData;
        expectedResult = service.addProgramacionToCollectionIfMissing([], programacion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(programacion);
      });

      it('should not add a Programacion to an array that contains it', () => {
        const programacion: IProgramacion = sampleWithRequiredData;
        const programacionCollection: IProgramacion[] = [
          {
            ...programacion,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProgramacionToCollectionIfMissing(programacionCollection, programacion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Programacion to an array that doesn't contain it", () => {
        const programacion: IProgramacion = sampleWithRequiredData;
        const programacionCollection: IProgramacion[] = [sampleWithPartialData];
        expectedResult = service.addProgramacionToCollectionIfMissing(programacionCollection, programacion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(programacion);
      });

      it('should add only unique Programacion to an array', () => {
        const programacionArray: IProgramacion[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const programacionCollection: IProgramacion[] = [sampleWithRequiredData];
        expectedResult = service.addProgramacionToCollectionIfMissing(programacionCollection, ...programacionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const programacion: IProgramacion = sampleWithRequiredData;
        const programacion2: IProgramacion = sampleWithPartialData;
        expectedResult = service.addProgramacionToCollectionIfMissing([], programacion, programacion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(programacion);
        expect(expectedResult).toContain(programacion2);
      });

      it('should accept null and undefined values', () => {
        const programacion: IProgramacion = sampleWithRequiredData;
        expectedResult = service.addProgramacionToCollectionIfMissing([], null, programacion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(programacion);
      });

      it('should return initial array if no Programacion is added', () => {
        const programacionCollection: IProgramacion[] = [sampleWithRequiredData];
        expectedResult = service.addProgramacionToCollectionIfMissing(programacionCollection, undefined, null);
        expect(expectedResult).toEqual(programacionCollection);
      });
    });

    describe('compareProgramacion', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProgramacion(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProgramacion(entity1, entity2);
        const compareResult2 = service.compareProgramacion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProgramacion(entity1, entity2);
        const compareResult2 = service.compareProgramacion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProgramacion(entity1, entity2);
        const compareResult2 = service.compareProgramacion(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
