import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEspecialista } from '../especialista.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../especialista.test-samples';

import { EspecialistaService } from './especialista.service';

const requireRestSample: IEspecialista = {
  ...sampleWithRequiredData,
};

describe('Especialista Service', () => {
  let service: EspecialistaService;
  let httpMock: HttpTestingController;
  let expectedResult: IEspecialista | IEspecialista[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EspecialistaService);
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

    it('should create a Especialista', () => {
      const especialista = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(especialista).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Especialista', () => {
      const especialista = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(especialista).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Especialista', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Especialista', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Especialista', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEspecialistaToCollectionIfMissing', () => {
      it('should add a Especialista to an empty array', () => {
        const especialista: IEspecialista = sampleWithRequiredData;
        expectedResult = service.addEspecialistaToCollectionIfMissing([], especialista);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(especialista);
      });

      it('should not add a Especialista to an array that contains it', () => {
        const especialista: IEspecialista = sampleWithRequiredData;
        const especialistaCollection: IEspecialista[] = [
          {
            ...especialista,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEspecialistaToCollectionIfMissing(especialistaCollection, especialista);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Especialista to an array that doesn't contain it", () => {
        const especialista: IEspecialista = sampleWithRequiredData;
        const especialistaCollection: IEspecialista[] = [sampleWithPartialData];
        expectedResult = service.addEspecialistaToCollectionIfMissing(especialistaCollection, especialista);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(especialista);
      });

      it('should add only unique Especialista to an array', () => {
        const especialistaArray: IEspecialista[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const especialistaCollection: IEspecialista[] = [sampleWithRequiredData];
        expectedResult = service.addEspecialistaToCollectionIfMissing(especialistaCollection, ...especialistaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const especialista: IEspecialista = sampleWithRequiredData;
        const especialista2: IEspecialista = sampleWithPartialData;
        expectedResult = service.addEspecialistaToCollectionIfMissing([], especialista, especialista2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(especialista);
        expect(expectedResult).toContain(especialista2);
      });

      it('should accept null and undefined values', () => {
        const especialista: IEspecialista = sampleWithRequiredData;
        expectedResult = service.addEspecialistaToCollectionIfMissing([], null, especialista, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(especialista);
      });

      it('should return initial array if no Especialista is added', () => {
        const especialistaCollection: IEspecialista[] = [sampleWithRequiredData];
        expectedResult = service.addEspecialistaToCollectionIfMissing(especialistaCollection, undefined, null);
        expect(expectedResult).toEqual(especialistaCollection);
      });
    });

    describe('compareEspecialista', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEspecialista(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEspecialista(entity1, entity2);
        const compareResult2 = service.compareEspecialista(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEspecialista(entity1, entity2);
        const compareResult2 = service.compareEspecialista(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEspecialista(entity1, entity2);
        const compareResult2 = service.compareEspecialista(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
