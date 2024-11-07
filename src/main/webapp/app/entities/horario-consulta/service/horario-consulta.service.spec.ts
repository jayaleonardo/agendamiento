import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IHorarioConsulta } from '../horario-consulta.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../horario-consulta.test-samples';

import { HorarioConsultaService, RestHorarioConsulta } from './horario-consulta.service';

const requireRestSample: RestHorarioConsulta = {
  ...sampleWithRequiredData,
  fechaHorario: sampleWithRequiredData.fechaHorario?.format(DATE_FORMAT),
  horaInicio: sampleWithRequiredData.horaInicio?.toJSON(),
  desdeHoraAlmuerzo: sampleWithRequiredData.desdeHoraAlmuerzo?.toJSON(),
  hastaHoraAlmuerzo: sampleWithRequiredData.hastaHoraAlmuerzo?.toJSON(),
};

describe('HorarioConsulta Service', () => {
  let service: HorarioConsultaService;
  let httpMock: HttpTestingController;
  let expectedResult: IHorarioConsulta | IHorarioConsulta[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(HorarioConsultaService);
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

    it('should create a HorarioConsulta', () => {
      const horarioConsulta = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(horarioConsulta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HorarioConsulta', () => {
      const horarioConsulta = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(horarioConsulta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a HorarioConsulta', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of HorarioConsulta', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a HorarioConsulta', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addHorarioConsultaToCollectionIfMissing', () => {
      it('should add a HorarioConsulta to an empty array', () => {
        const horarioConsulta: IHorarioConsulta = sampleWithRequiredData;
        expectedResult = service.addHorarioConsultaToCollectionIfMissing([], horarioConsulta);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(horarioConsulta);
      });

      it('should not add a HorarioConsulta to an array that contains it', () => {
        const horarioConsulta: IHorarioConsulta = sampleWithRequiredData;
        const horarioConsultaCollection: IHorarioConsulta[] = [
          {
            ...horarioConsulta,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addHorarioConsultaToCollectionIfMissing(horarioConsultaCollection, horarioConsulta);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HorarioConsulta to an array that doesn't contain it", () => {
        const horarioConsulta: IHorarioConsulta = sampleWithRequiredData;
        const horarioConsultaCollection: IHorarioConsulta[] = [sampleWithPartialData];
        expectedResult = service.addHorarioConsultaToCollectionIfMissing(horarioConsultaCollection, horarioConsulta);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(horarioConsulta);
      });

      it('should add only unique HorarioConsulta to an array', () => {
        const horarioConsultaArray: IHorarioConsulta[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const horarioConsultaCollection: IHorarioConsulta[] = [sampleWithRequiredData];
        expectedResult = service.addHorarioConsultaToCollectionIfMissing(horarioConsultaCollection, ...horarioConsultaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const horarioConsulta: IHorarioConsulta = sampleWithRequiredData;
        const horarioConsulta2: IHorarioConsulta = sampleWithPartialData;
        expectedResult = service.addHorarioConsultaToCollectionIfMissing([], horarioConsulta, horarioConsulta2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(horarioConsulta);
        expect(expectedResult).toContain(horarioConsulta2);
      });

      it('should accept null and undefined values', () => {
        const horarioConsulta: IHorarioConsulta = sampleWithRequiredData;
        expectedResult = service.addHorarioConsultaToCollectionIfMissing([], null, horarioConsulta, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(horarioConsulta);
      });

      it('should return initial array if no HorarioConsulta is added', () => {
        const horarioConsultaCollection: IHorarioConsulta[] = [sampleWithRequiredData];
        expectedResult = service.addHorarioConsultaToCollectionIfMissing(horarioConsultaCollection, undefined, null);
        expect(expectedResult).toEqual(horarioConsultaCollection);
      });
    });

    describe('compareHorarioConsulta', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareHorarioConsulta(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareHorarioConsulta(entity1, entity2);
        const compareResult2 = service.compareHorarioConsulta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareHorarioConsulta(entity1, entity2);
        const compareResult2 = service.compareHorarioConsulta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareHorarioConsulta(entity1, entity2);
        const compareResult2 = service.compareHorarioConsulta(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
