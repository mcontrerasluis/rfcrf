import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TcTipoImpService } from 'app/entities/tc-tipo-imp/tc-tipo-imp.service';
import { ITcTipoImp, TcTipoImp } from 'app/shared/model/tc-tipo-imp.model';

describe('Service Tests', () => {
  describe('TcTipoImp Service', () => {
    let injector: TestBed;
    let service: TcTipoImpService;
    let httpMock: HttpTestingController;
    let elemDefault: ITcTipoImp;
    let expectedResult: ITcTipoImp | ITcTipoImp[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TcTipoImpService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TcTipoImp(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 'AAAAAAA');
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

      it('should create a TcTipoImp', () => {
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

        service.create(new TcTipoImp()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TcTipoImp', () => {
        const returnedFromService = Object.assign(
          {
            clave: 'BBBBBB',
            descripcion: 'BBBBBB',
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

      it('should return a list of TcTipoImp', () => {
        const returnedFromService = Object.assign(
          {
            clave: 'BBBBBB',
            descripcion: 'BBBBBB',
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

      it('should delete a TcTipoImp', () => {
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
