import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TcManifesService } from 'app/entities/tc-manifes/tc-manifes.service';
import { ITcManifes, TcManifes } from 'app/shared/model/tc-manifes.model';

describe('Service Tests', () => {
  describe('TcManifes Service', () => {
    let injector: TestBed;
    let service: TcManifesService;
    let httpMock: HttpTestingController;
    let elemDefault: ITcManifes;
    let expectedResult: ITcManifes | ITcManifes[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TcManifesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TcManifes(0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, currentDate, 'AAAAAAA');
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

      it('should create a TcManifes', () => {
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

        service.create(new TcManifes()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TcManifes', () => {
        const returnedFromService = Object.assign(
          {
            clave: 'BBBBBB',
            descripcion: 'BBBBBB',
            moral: 1,
            fisica: 1,
            isr: 1,
            iva: 1,
            rfnorte: 1,
            rfsur: 1,
            s01: 1,
            s02: 1,
            s03: 1,
            s04: 1,
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

      it('should return a list of TcManifes', () => {
        const returnedFromService = Object.assign(
          {
            clave: 'BBBBBB',
            descripcion: 'BBBBBB',
            moral: 1,
            fisica: 1,
            isr: 1,
            iva: 1,
            rfnorte: 1,
            rfsur: 1,
            s01: 1,
            s02: 1,
            s03: 1,
            s04: 1,
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

      it('should delete a TcManifes', () => {
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
