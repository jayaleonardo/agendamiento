import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '2e8706dd-4558-43f2-b2d4-3f4a6bc9ffb3',
};

export const sampleWithPartialData: IAuthority = {
  name: 'ab26bc13-ee20-4494-a06a-7564e7097089',
};

export const sampleWithFullData: IAuthority = {
  name: '6f7ad9d1-b120-486e-9f50-9f1a11299284',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
