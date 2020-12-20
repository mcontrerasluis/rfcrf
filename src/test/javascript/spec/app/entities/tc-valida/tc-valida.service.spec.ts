import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TcValidaService } from 'app/entities/tc-valida/tc-valida.service';
import { ITcValida, TcValida } from 'app/shared/model/tc-valida.model';

describe('Service Tests', () => {
  describe('TcValida Service', () => {
    let injector: TestBed;
    let service: TcValidaService;
    let httpMock: HttpTestingController;
    let elemDefault: ITcValida;
    let expectedResult: ITcValida | ITcValida[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TcValidaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TcValida(0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TcValida', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TcValida()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TcValida', () => {
        const returnedFromService = Object.assign(
          {
            clave: 'BBBBBB',
            descripcion: 'BBBBBB',
            moral: 1,
            fisica: 1,
            isr: 1,
            iva: 1,
            usuario: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TcValida', () => {
        const returnedFromService = Object.assign(
          {
            clave: 'BBBBBB',
            descripcion: 'BBBBBB',
            moral: 1,
            fisica: 1,
            isr: 1,
            iva: 1,
            usuario: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TcValida', () => {
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
