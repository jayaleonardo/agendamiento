import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IContacto } from '../contacto.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../contacto.test-samples';

import { ContactoService } from './contacto.service';

const requireRestSample: IContacto = {
  ...sampleWithRequiredData,
};

describe('Contacto Service', () => {
  let service: ContactoService;
  let httpMock: HttpTestingController;
  let expectedResult: IContacto | IContacto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ContactoService);
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

    it('should create a Contacto', () => {
      const contacto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(contacto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Contacto', () => {
      const contacto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(contacto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Contacto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Contacto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Contacto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContactoToCollectionIfMissing', () => {
      it('should add a Contacto to an empty array', () => {
        const contacto: IContacto = sampleWithRequiredData;
        expectedResult = service.addContactoToCollectionIfMissing([], contacto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contacto);
      });

      it('should not add a Contacto to an array that contains it', () => {
        const contacto: IContacto = sampleWithRequiredData;
        const contactoCollection: IContacto[] = [
          {
            ...contacto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContactoToCollectionIfMissing(contactoCollection, contacto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Contacto to an array that doesn't contain it", () => {
        const contacto: IContacto = sampleWithRequiredData;
        const contactoCollection: IContacto[] = [sampleWithPartialData];
        expectedResult = service.addContactoToCollectionIfMissing(contactoCollection, contacto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contacto);
      });

      it('should add only unique Contacto to an array', () => {
        const contactoArray: IContacto[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const contactoCollection: IContacto[] = [sampleWithRequiredData];
        expectedResult = service.addContactoToCollectionIfMissing(contactoCollection, ...contactoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contacto: IContacto = sampleWithRequiredData;
        const contacto2: IContacto = sampleWithPartialData;
        expectedResult = service.addContactoToCollectionIfMissing([], contacto, contacto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contacto);
        expect(expectedResult).toContain(contacto2);
      });

      it('should accept null and undefined values', () => {
        const contacto: IContacto = sampleWithRequiredData;
        expectedResult = service.addContactoToCollectionIfMissing([], null, contacto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contacto);
      });

      it('should return initial array if no Contacto is added', () => {
        const contactoCollection: IContacto[] = [sampleWithRequiredData];
        expectedResult = service.addContactoToCollectionIfMissing(contactoCollection, undefined, null);
        expect(expectedResult).toEqual(contactoCollection);
      });
    });

    describe('compareContacto', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContacto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareContacto(entity1, entity2);
        const compareResult2 = service.compareContacto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareContacto(entity1, entity2);
        const compareResult2 = service.compareContacto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareContacto(entity1, entity2);
        const compareResult2 = service.compareContacto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
