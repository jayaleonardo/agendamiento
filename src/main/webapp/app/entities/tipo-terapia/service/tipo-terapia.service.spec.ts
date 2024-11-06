import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITipoTerapia } from '../tipo-terapia.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../tipo-terapia.test-samples';

import { TipoTerapiaService } from './tipo-terapia.service';

const requireRestSample: ITipoTerapia = {
  ...sampleWithRequiredData,
};

describe('TipoTerapia Service', () => {
  let service: TipoTerapiaService;
  let httpMock: HttpTestingController;
  let expectedResult: ITipoTerapia | ITipoTerapia[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TipoTerapiaService);
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

    it('should create a TipoTerapia', () => {
      const tipoTerapia = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tipoTerapia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TipoTerapia', () => {
      const tipoTerapia = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tipoTerapia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TipoTerapia', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TipoTerapia', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TipoTerapia', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTipoTerapiaToCollectionIfMissing', () => {
      it('should add a TipoTerapia to an empty array', () => {
        const tipoTerapia: ITipoTerapia = sampleWithRequiredData;
        expectedResult = service.addTipoTerapiaToCollectionIfMissing([], tipoTerapia);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tipoTerapia);
      });

      it('should not add a TipoTerapia to an array that contains it', () => {
        const tipoTerapia: ITipoTerapia = sampleWithRequiredData;
        const tipoTerapiaCollection: ITipoTerapia[] = [
          {
            ...tipoTerapia,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTipoTerapiaToCollectionIfMissing(tipoTerapiaCollection, tipoTerapia);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TipoTerapia to an array that doesn't contain it", () => {
        const tipoTerapia: ITipoTerapia = sampleWithRequiredData;
        const tipoTerapiaCollection: ITipoTerapia[] = [sampleWithPartialData];
        expectedResult = service.addTipoTerapiaToCollectionIfMissing(tipoTerapiaCollection, tipoTerapia);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tipoTerapia);
      });

      it('should add only unique TipoTerapia to an array', () => {
        const tipoTerapiaArray: ITipoTerapia[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tipoTerapiaCollection: ITipoTerapia[] = [sampleWithRequiredData];
        expectedResult = service.addTipoTerapiaToCollectionIfMissing(tipoTerapiaCollection, ...tipoTerapiaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tipoTerapia: ITipoTerapia = sampleWithRequiredData;
        const tipoTerapia2: ITipoTerapia = sampleWithPartialData;
        expectedResult = service.addTipoTerapiaToCollectionIfMissing([], tipoTerapia, tipoTerapia2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tipoTerapia);
        expect(expectedResult).toContain(tipoTerapia2);
      });

      it('should accept null and undefined values', () => {
        const tipoTerapia: ITipoTerapia = sampleWithRequiredData;
        expectedResult = service.addTipoTerapiaToCollectionIfMissing([], null, tipoTerapia, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tipoTerapia);
      });

      it('should return initial array if no TipoTerapia is added', () => {
        const tipoTerapiaCollection: ITipoTerapia[] = [sampleWithRequiredData];
        expectedResult = service.addTipoTerapiaToCollectionIfMissing(tipoTerapiaCollection, undefined, null);
        expect(expectedResult).toEqual(tipoTerapiaCollection);
      });
    });

    describe('compareTipoTerapia', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTipoTerapia(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTipoTerapia(entity1, entity2);
        const compareResult2 = service.compareTipoTerapia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTipoTerapia(entity1, entity2);
        const compareResult2 = service.compareTipoTerapia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTipoTerapia(entity1, entity2);
        const compareResult2 = service.compareTipoTerapia(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
