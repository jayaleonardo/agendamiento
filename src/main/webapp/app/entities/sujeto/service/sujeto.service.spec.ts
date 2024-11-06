import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ISujeto } from '../sujeto.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../sujeto.test-samples';

import { RestSujeto, SujetoService } from './sujeto.service';

const requireRestSample: RestSujeto = {
  ...sampleWithRequiredData,
  fechaNacimiento: sampleWithRequiredData.fechaNacimiento?.format(DATE_FORMAT),
};

describe('Sujeto Service', () => {
  let service: SujetoService;
  let httpMock: HttpTestingController;
  let expectedResult: ISujeto | ISujeto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SujetoService);
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

    it('should create a Sujeto', () => {
      const sujeto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(sujeto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Sujeto', () => {
      const sujeto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(sujeto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Sujeto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Sujeto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Sujeto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSujetoToCollectionIfMissing', () => {
      it('should add a Sujeto to an empty array', () => {
        const sujeto: ISujeto = sampleWithRequiredData;
        expectedResult = service.addSujetoToCollectionIfMissing([], sujeto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sujeto);
      });

      it('should not add a Sujeto to an array that contains it', () => {
        const sujeto: ISujeto = sampleWithRequiredData;
        const sujetoCollection: ISujeto[] = [
          {
            ...sujeto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSujetoToCollectionIfMissing(sujetoCollection, sujeto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Sujeto to an array that doesn't contain it", () => {
        const sujeto: ISujeto = sampleWithRequiredData;
        const sujetoCollection: ISujeto[] = [sampleWithPartialData];
        expectedResult = service.addSujetoToCollectionIfMissing(sujetoCollection, sujeto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sujeto);
      });

      it('should add only unique Sujeto to an array', () => {
        const sujetoArray: ISujeto[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const sujetoCollection: ISujeto[] = [sampleWithRequiredData];
        expectedResult = service.addSujetoToCollectionIfMissing(sujetoCollection, ...sujetoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sujeto: ISujeto = sampleWithRequiredData;
        const sujeto2: ISujeto = sampleWithPartialData;
        expectedResult = service.addSujetoToCollectionIfMissing([], sujeto, sujeto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sujeto);
        expect(expectedResult).toContain(sujeto2);
      });

      it('should accept null and undefined values', () => {
        const sujeto: ISujeto = sampleWithRequiredData;
        expectedResult = service.addSujetoToCollectionIfMissing([], null, sujeto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sujeto);
      });

      it('should return initial array if no Sujeto is added', () => {
        const sujetoCollection: ISujeto[] = [sampleWithRequiredData];
        expectedResult = service.addSujetoToCollectionIfMissing(sujetoCollection, undefined, null);
        expect(expectedResult).toEqual(sujetoCollection);
      });
    });

    describe('compareSujeto', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSujeto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSujeto(entity1, entity2);
        const compareResult2 = service.compareSujeto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSujeto(entity1, entity2);
        const compareResult2 = service.compareSujeto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSujeto(entity1, entity2);
        const compareResult2 = service.compareSujeto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
