import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TcTipoSolComponentsPage, TcTipoSolDeleteDialog, TcTipoSolUpdatePage } from './tc-tipo-sol.page-object';

const expect = chai.expect;

describe('TcTipoSol e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tcTipoSolComponentsPage: TcTipoSolComponentsPage;
  let tcTipoSolUpdatePage: TcTipoSolUpdatePage;
  let tcTipoSolDeleteDialog: TcTipoSolDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TcTipoSols', async () => {
    await navBarPage.goToEntity('tc-tipo-sol');
    tcTipoSolComponentsPage = new TcTipoSolComponentsPage();
    await browser.wait(ec.visibilityOf(tcTipoSolComponentsPage.title), 5000);
    expect(await tcTipoSolComponentsPage.getTitle()).to.eq('rfcrfApp.tcTipoSol.home.title');
    await browser.wait(ec.or(ec.visibilityOf(tcTipoSolComponentsPage.entities), ec.visibilityOf(tcTipoSolComponentsPage.noResult)), 1000);
  });

  it('should load create TcTipoSol page', async () => {
    await tcTipoSolComponentsPage.clickOnCreateButton();
    tcTipoSolUpdatePage = new TcTipoSolUpdatePage();
    expect(await tcTipoSolUpdatePage.getPageTitle()).to.eq('rfcrfApp.tcTipoSol.home.createOrEditLabel');
    await tcTipoSolUpdatePage.cancel();
  });

  it('should create and save TcTipoSols', async () => {
    const nbButtonsBeforeCreate = await tcTipoSolComponentsPage.countDeleteButtons();

    await tcTipoSolComponentsPage.clickOnCreateButton();

    await promise.all([
      tcTipoSolUpdatePage.setClaveInput('clave'),
      tcTipoSolUpdatePage.setDescripcionInput('descripcion'),
      tcTipoSolUpdatePage.setIsrInput('5'),
      tcTipoSolUpdatePage.setIvaInput('5'),
      tcTipoSolUpdatePage.setEfirmaInput('5'),
      tcTipoSolUpdatePage.setFechaInicioInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tcTipoSolUpdatePage.setFechaFinInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tcTipoSolUpdatePage.setUsuarioInput('usuario'),
    ]);

    expect(await tcTipoSolUpdatePage.getClaveInput()).to.eq('clave', 'Expected Clave value to be equals to clave');
    expect(await tcTipoSolUpdatePage.getDescripcionInput()).to.eq('descripcion', 'Expected Descripcion value to be equals to descripcion');
    expect(await tcTipoSolUpdatePage.getIsrInput()).to.eq('5', 'Expected isr value to be equals to 5');
    expect(await tcTipoSolUpdatePage.getIvaInput()).to.eq('5', 'Expected iva value to be equals to 5');
    expect(await tcTipoSolUpdatePage.getEfirmaInput()).to.eq('5', 'Expected efirma value to be equals to 5');
    expect(await tcTipoSolUpdatePage.getFechaInicioInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fechaInicio value to be equals to 2000-12-31'
    );
    expect(await tcTipoSolUpdatePage.getFechaFinInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fechaFin value to be equals to 2000-12-31'
    );
    expect(await tcTipoSolUpdatePage.getUsuarioInput()).to.eq('usuario', 'Expected Usuario value to be equals to usuario');

    await tcTipoSolUpdatePage.save();
    expect(await tcTipoSolUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tcTipoSolComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TcTipoSol', async () => {
    const nbButtonsBeforeDelete = await tcTipoSolComponentsPage.countDeleteButtons();
    await tcTipoSolComponentsPage.clickOnLastDeleteButton();

    tcTipoSolDeleteDialog = new TcTipoSolDeleteDialog();
    expect(await tcTipoSolDeleteDialog.getDialogTitle()).to.eq('rfcrfApp.tcTipoSol.delete.question');
    await tcTipoSolDeleteDialog.clickOnConfirmButton();

    expect(await tcTipoSolComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
