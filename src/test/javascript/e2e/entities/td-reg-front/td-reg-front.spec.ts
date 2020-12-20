import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TdRegFrontComponentsPage, TdRegFrontDeleteDialog, TdRegFrontUpdatePage } from './td-reg-front.page-object';

const expect = chai.expect;

describe('TdRegFront e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tdRegFrontComponentsPage: TdRegFrontComponentsPage;
  let tdRegFrontUpdatePage: TdRegFrontUpdatePage;
  let tdRegFrontDeleteDialog: TdRegFrontDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TdRegFronts', async () => {
    await navBarPage.goToEntity('td-reg-front');
    tdRegFrontComponentsPage = new TdRegFrontComponentsPage();
    await browser.wait(ec.visibilityOf(tdRegFrontComponentsPage.title), 5000);
    expect(await tdRegFrontComponentsPage.getTitle()).to.eq('rfcrfApp.tdRegFront.home.title');
    await browser.wait(ec.or(ec.visibilityOf(tdRegFrontComponentsPage.entities), ec.visibilityOf(tdRegFrontComponentsPage.noResult)), 1000);
  });

  it('should load create TdRegFront page', async () => {
    await tdRegFrontComponentsPage.clickOnCreateButton();
    tdRegFrontUpdatePage = new TdRegFrontUpdatePage();
    expect(await tdRegFrontUpdatePage.getPageTitle()).to.eq('rfcrfApp.tdRegFront.home.createOrEditLabel');
    await tdRegFrontUpdatePage.cancel();
  });

  it('should create and save TdRegFronts', async () => {
    const nbButtonsBeforeCreate = await tdRegFrontComponentsPage.countDeleteButtons();

    await tdRegFrontComponentsPage.clickOnCreateButton();

    await promise.all([
      tdRegFrontUpdatePage.setRegionInput('region'),
      tdRegFrontUpdatePage.setDomicilioRegionInput('domicilioRegion'),
      tdRegFrontUpdatePage.setSucursalRegionInput('sucursalRegion'),
      tdRegFrontUpdatePage.setTipoImpuestodInput('tipoImpuestod'),
      tdRegFrontUpdatePage.setTipoSolicituddInput('tipoSolicitudd'),
      tdRegFrontUpdatePage.setEjerciciodInput('5'),
      tdRegFrontUpdatePage.setEstatusInput('estatus'),
      tdRegFrontUpdatePage.setFolioInput('folio'),
      tdRegFrontUpdatePage.setRfcInput('rfc'),
      tdRegFrontUpdatePage.setFechaInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tdRegFrontUpdatePage.setUsuarioInput('usuario'),
      tdRegFrontUpdatePage.tipoSolicitudSelectLastOption(),
      tdRegFrontUpdatePage.tipoImpuestoSelectLastOption(),
      tdRegFrontUpdatePage.ejercicioSelectLastOption(),
      // tdRegFrontUpdatePage.manifestacionSelectLastOption(),
      // tdRegFrontUpdatePage.validacionSelectLastOption(),
    ]);

    expect(await tdRegFrontUpdatePage.getRegionInput()).to.eq('region', 'Expected Region value to be equals to region');
    expect(await tdRegFrontUpdatePage.getDomicilioRegionInput()).to.eq(
      'domicilioRegion',
      'Expected DomicilioRegion value to be equals to domicilioRegion'
    );
    expect(await tdRegFrontUpdatePage.getSucursalRegionInput()).to.eq(
      'sucursalRegion',
      'Expected SucursalRegion value to be equals to sucursalRegion'
    );
    expect(await tdRegFrontUpdatePage.getTipoImpuestodInput()).to.eq(
      'tipoImpuestod',
      'Expected TipoImpuestod value to be equals to tipoImpuestod'
    );
    expect(await tdRegFrontUpdatePage.getTipoSolicituddInput()).to.eq(
      'tipoSolicitudd',
      'Expected TipoSolicitudd value to be equals to tipoSolicitudd'
    );
    expect(await tdRegFrontUpdatePage.getEjerciciodInput()).to.eq('5', 'Expected ejerciciod value to be equals to 5');
    expect(await tdRegFrontUpdatePage.getEstatusInput()).to.eq('estatus', 'Expected Estatus value to be equals to estatus');
    expect(await tdRegFrontUpdatePage.getFolioInput()).to.eq('folio', 'Expected Folio value to be equals to folio');
    expect(await tdRegFrontUpdatePage.getRfcInput()).to.eq('rfc', 'Expected Rfc value to be equals to rfc');
    expect(await tdRegFrontUpdatePage.getFechaInput()).to.contain('2001-01-01T02:30', 'Expected fecha value to be equals to 2000-12-31');
    expect(await tdRegFrontUpdatePage.getUsuarioInput()).to.eq('usuario', 'Expected Usuario value to be equals to usuario');

    await tdRegFrontUpdatePage.save();
    expect(await tdRegFrontUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tdRegFrontComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TdRegFront', async () => {
    const nbButtonsBeforeDelete = await tdRegFrontComponentsPage.countDeleteButtons();
    await tdRegFrontComponentsPage.clickOnLastDeleteButton();

    tdRegFrontDeleteDialog = new TdRegFrontDeleteDialog();
    expect(await tdRegFrontDeleteDialog.getDialogTitle()).to.eq('rfcrfApp.tdRegFront.delete.question');
    await tdRegFrontDeleteDialog.clickOnConfirmButton();

    expect(await tdRegFrontComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
