import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TcEjercService } from 'app/entities/tc-ejerc/tc-ejerc.service';
import { ITcEjerc, TcEjerc } from 'app/shared/model/tc-ejerc.model';

describe('Service Tests', () => {
  describe('TcEjerc Service', () => {
    let injector: TestBed;
    let service: TcEjercService;
    let httpMock: HttpTestingController;
    let elemDefault: ITcEjerc;
    let expectedResult: ITcEjerc | ITcEjerc[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TcEjercService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TcEjerc(0, 'AAAAAAA', 0, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaInicio: currentDate.format(DATE_TIME_FORMAT),
            fechaFin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TcEjerc', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaInicio: currentDate.format(DATE_TIME_FORMAT),
            fechaFin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaInicio: currentDate,
            fechaFin: currentDate,
          },
          returnedFromService
        );

        service.create(new TcEjerc()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TcEjerc', () => {
        const returnedFromService = Object.assign(
          {
            clave: 'BBBBBB',
            valor: 1,
            fechaInicio: currentDate.format(DATE_TIME_FORMAT),
            fechaFin: currentDate.format(DATE_TIME_FORMAT),
            usuario: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaInicio: currentDate,
            fechaFin: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TcEjerc', () => {
        const returnedFromService = Object.assign(
          {
            clave: 'BBBBBB',
            valor: 1,
            fechaInicio: currentDate.format(DATE_TIME_FORMAT),
            fechaFin: currentDate.format(DATE_TIME_FORMAT),
            usuario: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaInicio: currentDate,
            fechaFin: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TcEjerc', () => {
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
