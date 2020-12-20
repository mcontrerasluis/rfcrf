import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TcManifesComponentsPage, TcManifesDeleteDialog, TcManifesUpdatePage } from './tc-manifes.page-object';

const expect = chai.expect;

describe('TcManifes e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tcManifesComponentsPage: TcManifesComponentsPage;
  let tcManifesUpdatePage: TcManifesUpdatePage;
  let tcManifesDeleteDialog: TcManifesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TcManifes', async () => {
    await navBarPage.goToEntity('tc-manifes');
    tcManifesComponentsPage = new TcManifesComponentsPage();
    await browser.wait(ec.visibilityOf(tcManifesComponentsPage.title), 5000);
    expect(await tcManifesComponentsPage.getTitle()).to.eq('rfcrfApp.tcManifes.home.title');
    await browser.wait(ec.or(ec.visibilityOf(tcManifesComponentsPage.entities), ec.visibilityOf(tcManifesComponentsPage.noResult)), 1000);
  });

  it('should load create TcManifes page', async () => {
    await tcManifesComponentsPage.clickOnCreateButton();
    tcManifesUpdatePage = new TcManifesUpdatePage();
    expect(await tcManifesUpdatePage.getPageTitle()).to.eq('rfcrfApp.tcManifes.home.createOrEditLabel');
    await tcManifesUpdatePage.cancel();
  });

  it('should create and save TcManifes', async () => {
    const nbButtonsBeforeCreate = await tcManifesComponentsPage.countDeleteButtons();

    await tcManifesComponentsPage.clickOnCreateButton();

    await promise.all([
      tcManifesUpdatePage.setClaveInput('clave'),
      tcManifesUpdatePage.setDescripcionInput('descripcion'),
      tcManifesUpdatePage.setMoralInput('5'),
      tcManifesUpdatePage.setFisicaInput('5'),
      tcManifesUpdatePage.setIsrInput('5'),
      tcManifesUpdatePage.setIvaInput('5'),
      tcManifesUpdatePage.setRfnorteInput('5'),
      tcManifesUpdatePage.setRfsurInput('5'),
      tcManifesUpdatePage.setS01Input('5'),
      tcManifesUpdatePage.setS02Input('5'),
      tcManifesUpdatePage.setS03Input('5'),
      tcManifesUpdatePage.setS04Input('5'),
      tcManifesUpdatePage.setFechaInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tcManifesUpdatePage.setUsuarioInput('usuario'),
    ]);

    expect(await tcManifesUpdatePage.getClaveInput()).to.eq('clave', 'Expected Clave value to be equals to clave');
    expect(await tcManifesUpdatePage.getDescripcionInput()).to.eq('descripcion', 'Expected Descripcion value to be equals to descripcion');
    expect(await tcManifesUpdatePage.getMoralInput()).to.eq('5', 'Expected moral value to be equals to 5');
    expect(await tcManifesUpdatePage.getFisicaInput()).to.eq('5', 'Expected fisica value to be equals to 5');
    expect(await tcManifesUpdatePage.getIsrInput()).to.eq('5', 'Expected isr value to be equals to 5');
    expect(await tcManifesUpdatePage.getIvaInput()).to.eq('5', 'Expected iva value to be equals to 5');
    expect(await tcManifesUpdatePage.getRfnorteInput()).to.eq('5', 'Expected rfnorte value to be equals to 5');
    expect(await tcManifesUpdatePage.getRfsurInput()).to.eq('5', 'Expected rfsur value to be equals to 5');
    expect(await tcManifesUpdatePage.getS01Input()).to.eq('5', 'Expected s01 value to be equals to 5');
    expect(await tcManifesUpdatePage.getS02Input()).to.eq('5', 'Expected s02 value to be equals to 5');
    expect(await tcManifesUpdatePage.getS03Input()).to.eq('5', 'Expected s03 value to be equals to 5');
    expect(await tcManifesUpdatePage.getS04Input()).to.eq('5', 'Expected s04 value to be equals to 5');
    expect(await tcManifesUpdatePage.getFechaInput()).to.contain('2001-01-01T02:30', 'Expected fecha value to be equals to 2000-12-31');
    expect(await tcManifesUpdatePage.getUsuarioInput()).to.eq('usuario', 'Expected Usuario value to be equals to usuario');

    await tcManifesUpdatePage.save();
    expect(await tcManifesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tcManifesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TcManifes', async () => {
    const nbButtonsBeforeDelete = await tcManifesComponentsPage.countDeleteButtons();
    await tcManifesComponentsPage.clickOnLastDeleteButton();

    tcManifesDeleteDialog = new TcManifesDeleteDialog();
    expect(await tcManifesDeleteDialog.getDialogTitle()).to.eq('rfcrfApp.tcManifes.delete.question');
    await tcManifesDeleteDialog.clickOnConfirmButton();

    expect(await tcManifesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
