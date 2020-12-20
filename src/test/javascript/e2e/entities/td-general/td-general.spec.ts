import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TdGeneralComponentsPage, TdGeneralDeleteDialog, TdGeneralUpdatePage } from './td-general.page-object';

const expect = chai.expect;

describe('TdGeneral e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tdGeneralComponentsPage: TdGeneralComponentsPage;
  let tdGeneralUpdatePage: TdGeneralUpdatePage;
  let tdGeneralDeleteDialog: TdGeneralDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TdGenerals', async () => {
    await navBarPage.goToEntity('td-general');
    tdGeneralComponentsPage = new TdGeneralComponentsPage();
    await browser.wait(ec.visibilityOf(tdGeneralComponentsPage.title), 5000);
    expect(await tdGeneralComponentsPage.getTitle()).to.eq('rfcrfApp.tdGeneral.home.title');
    await browser.wait(ec.or(ec.visibilityOf(tdGeneralComponentsPage.entities), ec.visibilityOf(tdGeneralComponentsPage.noResult)), 1000);
  });

  it('should load create TdGeneral page', async () => {
    await tdGeneralComponentsPage.clickOnCreateButton();
    tdGeneralUpdatePage = new TdGeneralUpdatePage();
    expect(await tdGeneralUpdatePage.getPageTitle()).to.eq('rfcrfApp.tdGeneral.home.createOrEditLabel');
    await tdGeneralUpdatePage.cancel();
  });

  it('should create and save TdGenerals', async () => {
    const nbButtonsBeforeCreate = await tdGeneralComponentsPage.countDeleteButtons();

    await tdGeneralComponentsPage.clickOnCreateButton();

    await promise.all([
      tdGeneralUpdatePage.setRfcInput('rfc'),
      tdGeneralUpdatePage.setFechaInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tdGeneralUpdatePage.setTipoSolicituddInput('tipoSolicitudd'),
      tdGeneralUpdatePage.setFolioInput('folio'),
      tdGeneralUpdatePage.setEstatusInput('5'),
      tdGeneralUpdatePage.tipoSolicitudSelectLastOption(),
    ]);

    expect(await tdGeneralUpdatePage.getRfcInput()).to.eq('rfc', 'Expected Rfc value to be equals to rfc');
    expect(await tdGeneralUpdatePage.getFechaInput()).to.contain('2001-01-01T02:30', 'Expected fecha value to be equals to 2000-12-31');
    expect(await tdGeneralUpdatePage.getTipoSolicituddInput()).to.eq(
      'tipoSolicitudd',
      'Expected TipoSolicitudd value to be equals to tipoSolicitudd'
    );
    expect(await tdGeneralUpdatePage.getFolioInput()).to.eq('folio', 'Expected Folio value to be equals to folio');
    expect(await tdGeneralUpdatePage.getEstatusInput()).to.eq('5', 'Expected estatus value to be equals to 5');

    await tdGeneralUpdatePage.save();
    expect(await tdGeneralUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tdGeneralComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TdGeneral', async () => {
    const nbButtonsBeforeDelete = await tdGeneralComponentsPage.countDeleteButtons();
    await tdGeneralComponentsPage.clickOnLastDeleteButton();

    tdGeneralDeleteDialog = new TdGeneralDeleteDialog();
    expect(await tdGeneralDeleteDialog.getDialogTitle()).to.eq('rfcrfApp.tdGeneral.delete.question');
    await tdGeneralDeleteDialog.clickOnConfirmButton();

    expect(await tdGeneralComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
