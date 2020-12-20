import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TdRegFrontService } from 'app/entities/td-reg-front/td-reg-front.service';
import { ITdRegFront, TdRegFront } from 'app/shared/model/td-reg-front.model';

describe('Service Tests', () => {
  describe('TdRegFront Service', () => {
    let injector: TestBed;
    let service: TdRegFrontService;
    let httpMock: HttpTestingController;
    let elemDefault: ITdRegFront;
    let expectedResult: ITdRegFront | ITdRegFront[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TdRegFrontService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TdRegFront(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA'
      );
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

      it('should create a TdRegFront', () => {
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

        service.create(new TdRegFront()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TdRegFront', () => {
        const returnedFromService = Object.assign(
          {
            region: 'BBBBBB',
            domicilioRegion: 'BBBBBB',
            sucursalRegion: 'BBBBBB',
            tipoImpuestod: 'BBBBBB',
            tipoSolicitudd: 'BBBBBB',
            ejerciciod: 1,
            estatus: 'BBBBBB',
            folio: 'BBBBBB',
            rfc: 'BBBBBB',
            fecha: currentDate.format(DATE_TIME_FORMAT),
            usuario: 'BBBBBB',
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

      it('should return a list of TdRegFront', () => {
        const returnedFromService = Object.assign(
          {
            region: 'BBBBBB',
            domicilioRegion: 'BBBBBB',
            sucursalRegion: 'BBBBBB',
            tipoImpuestod: 'BBBBBB',
            tipoSolicitudd: 'BBBBBB',
            ejerciciod: 1,
            estatus: 'BBBBBB',
            folio: 'BBBBBB',
            rfc: 'BBBBBB',
            fecha: currentDate.format(DATE_TIME_FORMAT),
            usuario: 'BBBBBB',
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

      it('should delete a TdRegFront', () => {
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
