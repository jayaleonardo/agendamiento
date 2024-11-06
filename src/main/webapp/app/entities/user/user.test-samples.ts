import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 13309,
  login: 'QK_JB',
};

export const sampleWithPartialData: IUser = {
  id: 13339,
  login: '0`@Ho\\Jt4Jbh0',
};

export const sampleWithFullData: IUser = {
  id: 22071,
  login: 'Rc',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
