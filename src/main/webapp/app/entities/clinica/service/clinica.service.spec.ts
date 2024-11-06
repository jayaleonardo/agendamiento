import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IClinica } from '../clinica.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../clinica.test-samples';

import { ClinicaService } from './clinica.service';

const requireRestSample: IClinica = {
  ...sampleWithRequiredData,
};

describe('Clinica Service', () => {
  let service: ClinicaService;
  let httpMock: HttpTestingController;
  let expectedResult: IClinica | IClinica[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ClinicaService);
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

    it('should create a Clinica', () => {
      const clinica = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(clinica).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Clinica', () => {
      const clinica = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(clinica).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Clinica', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Clinica', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Clinica', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addClinicaToCollectionIfMissing', () => {
      it('should add a Clinica to an empty array', () => {
        const clinica: IClinica = sampleWithRequiredData;
        expectedResult = service.addClinicaToCollectionIfMissing([], clinica);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(clinica);
      });

      it('should not add a Clinica to an array that contains it', () => {
        const clinica: IClinica = sampleWithRequiredData;
        const clinicaCollection: IClinica[] = [
          {
            ...clinica,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addClinicaToCollectionIfMissing(clinicaCollection, clinica);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Clinica to an array that doesn't contain it", () => {
        const clinica: IClinica = sampleWithRequiredData;
        const clinicaCollection: IClinica[] = [sampleWithPartialData];
        expectedResult = service.addClinicaToCollectionIfMissing(clinicaCollection, clinica);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(clinica);
      });

      it('should add only unique Clinica to an array', () => {
        const clinicaArray: IClinica[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const clinicaCollection: IClinica[] = [sampleWithRequiredData];
        expectedResult = service.addClinicaToCollectionIfMissing(clinicaCollection, ...clinicaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const clinica: IClinica = sampleWithRequiredData;
        const clinica2: IClinica = sampleWithPartialData;
        expectedResult = service.addClinicaToCollectionIfMissing([], clinica, clinica2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(clinica);
        expect(expectedResult).toContain(clinica2);
      });

      it('should accept null and undefined values', () => {
        const clinica: IClinica = sampleWithRequiredData;
        expectedResult = service.addClinicaToCollectionIfMissing([], null, clinica, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(clinica);
      });

      it('should return initial array if no Clinica is added', () => {
        const clinicaCollection: IClinica[] = [sampleWithRequiredData];
        expectedResult = service.addClinicaToCollectionIfMissing(clinicaCollection, undefined, null);
        expect(expectedResult).toEqual(clinicaCollection);
      });
    });

    describe('compareClinica', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareClinica(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareClinica(entity1, entity2);
        const compareResult2 = service.compareClinica(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareClinica(entity1, entity2);
        const compareResult2 = service.compareClinica(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareClinica(entity1, entity2);
        const compareResult2 = service.compareClinica(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
