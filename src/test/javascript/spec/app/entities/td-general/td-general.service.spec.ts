import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TdGeneralService } from 'app/entities/td-general/td-general.service';
import { ITdGeneral, TdGeneral } from 'app/shared/model/td-general.model';

describe('Service Tests', () => {
  describe('TdGeneral Service', () => {
    let injector: TestBed;
    let service: TdGeneralService;
    let httpMock: HttpTestingController;
    let elemDefault: ITdGeneral;
    let expectedResult: ITdGeneral | ITdGeneral[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TdGeneralService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TdGeneral(0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TdGeneral', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fecha: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
          },
          returnedFromService
        );

        service.create(new TdGeneral()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TdGeneral', () => {
        const returnedFromService = Object.assign(
          {
            rfc: 'BBBBBB',
            fecha: currentDate.format(DATE_TIME_FORMAT),
            tipoSolicitudd: 'BBBBBB',
            folio: 'BBBBBB',
            estatus: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TdGeneral', () => {
        const returnedFromService = Object.assign(
          {
            rfc: 'BBBBBB',
            fecha: currentDate.format(DATE_TIME_FORMAT),
            tipoSolicitudd: 'BBBBBB',
            folio: 'BBBBBB',
            estatus: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TdGeneral', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
