import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TcTipoImpComponentsPage, TcTipoImpDeleteDialog, TcTipoImpUpdatePage } from './tc-tipo-imp.page-object';

const expect = chai.expect;

describe('TcTipoImp e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tcTipoImpComponentsPage: TcTipoImpComponentsPage;
  let tcTipoImpUpdatePage: TcTipoImpUpdatePage;
  let tcTipoImpDeleteDialog: TcTipoImpDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TcTipoImps', async () => {
    await navBarPage.goToEntity('tc-tipo-imp');
    tcTipoImpComponentsPage = new TcTipoImpComponentsPage();
    await browser.wait(ec.visibilityOf(tcTipoImpComponentsPage.title), 5000);
    expect(await tcTipoImpComponentsPage.getTitle()).to.eq('rfcrfApp.tcTipoImp.home.title');
    await browser.wait(ec.or(ec.visibilityOf(tcTipoImpComponentsPage.entities), ec.visibilityOf(tcTipoImpComponentsPage.noResult)), 1000);
  });

  it('should load create TcTipoImp page', async () => {
    await tcTipoImpComponentsPage.clickOnCreateButton();
    tcTipoImpUpdatePage = new TcTipoImpUpdatePage();
    expect(await tcTipoImpUpdatePage.getPageTitle()).to.eq('rfcrfApp.tcTipoImp.home.createOrEditLabel');
    await tcTipoImpUpdatePage.cancel();
  });

  it('should create and save TcTipoImps', async () => {
    const nbButtonsBeforeCreate = await tcTipoImpComponentsPage.countDeleteButtons();

    await tcTipoImpComponentsPage.clickOnCreateButton();

    await promise.all([
      tcTipoImpUpdatePage.setClaveInput('clave'),
      tcTipoImpUpdatePage.setDescripcionInput('descripcion'),
      tcTipoImpUpdatePage.setFechaInicioInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tcTipoImpUpdatePage.setFechaFinInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tcTipoImpUpdatePage.setUsuarioInput('usuario'),
    ]);

    expect(await tcTipoImpUpdatePage.getClaveInput()).to.eq('clave', 'Expected Clave value to be equals to clave');
    expect(await tcTipoImpUpdatePage.getDescripcionInput()).to.eq('descripcion', 'Expected Descripcion value to be equals to descripcion');
    expect(await tcTipoImpUpdatePage.getFechaInicioInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fechaInicio value to be equals to 2000-12-31'
    );
    expect(await tcTipoImpUpdatePage.getFechaFinInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fechaFin value to be equals to 2000-12-31'
    );
    expect(await tcTipoImpUpdatePage.getUsuarioInput()).to.eq('usuario', 'Expected Usuario value to be equals to usuario');

    await tcTipoImpUpdatePage.save();
    expect(await tcTipoImpUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tcTipoImpComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TcTipoImp', async () => {
    const nbButtonsBeforeDelete = await tcTipoImpComponentsPage.countDeleteButtons();
    await tcTipoImpComponentsPage.clickOnLastDeleteButton();

    tcTipoImpDeleteDialog = new TcTipoImpDeleteDialog();
    expect(await tcTipoImpDeleteDialog.getDialogTitle()).to.eq('rfcrfApp.tcTipoImp.delete.question');
    await tcTipoImpDeleteDialog.clickOnConfirmButton();

    expect(await tcTipoImpComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
