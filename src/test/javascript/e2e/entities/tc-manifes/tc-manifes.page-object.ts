import { element, by, ElementFinder } from 'protractor';

export class TcManifesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-tc-manifes div table .btn-danger'));
  title = element.all(by.css('jhi-tc-manifes div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class TcManifesUpdatePage {
  pageTitle = element(by.id('jhi-tc-manifes-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  claveInput = element(by.id('field_clave'));
  descripcionInput = element(by.id('field_descripcion'));
  moralInput = element(by.id('field_moral'));
  fisicaInput = element(by.id('field_fisica'));
  isrInput = element(by.id('field_isr'));
  ivaInput = element(by.id('field_iva'));
  rfnorteInput = element(by.id('field_rfnorte'));
  rfsurInput = element(by.id('field_rfsur'));
  s01Input = element(by.id('field_s01'));
  s02Input = element(by.id('field_s02'));
  s03Input = element(by.id('field_s03'));
  s04Input = element(by.id('field_s04'));
  fechaInput = element(by.id('field_fecha'));
  usuarioInput = element(by.id('field_usuario'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setClaveInput(clave: string): Promise<void> {
    await this.claveInput.sendKeys(clave);
  }

  async getClaveInput(): Promise<string> {
    return await this.claveInput.getAttribute('value');
  }

  async setDescripcionInput(descripcion: string): Promise<void> {
    await this.descripcionInput.sendKeys(descripcion);
  }

  async getDescripcionInput(): Promise<string> {
    return await this.descripcionInput.getAttribute('value');
  }

  async setMoralInput(moral: string): Promise<void> {
    await this.moralInput.sendKeys(moral);
  }

  async getMoralInput(): Promise<string> {
    return await this.moralInput.getAttribute('value');
  }

  async setFisicaInput(fisica: string): Promise<void> {
    await this.fisicaInput.sendKeys(fisica);
  }

  async getFisicaInput(): Promise<string> {
    return await this.fisicaInput.getAttribute('value');
  }

  async setIsrInput(isr: string): Promise<void> {
    await this.isrInput.sendKeys(isr);
  }

  async getIsrInput(): Promise<string> {
    return await this.isrInput.getAttribute('value');
  }

  async setIvaInput(iva: string): Promise<void> {
    await this.ivaInput.sendKeys(iva);
  }

  async getIvaInput(): Promise<string> {
    return await this.ivaInput.getAttribute('value');
  }

  async setRfnorteInput(rfnorte: string): Promise<void> {
    await this.rfnorteInput.sendKeys(rfnorte);
  }

  async getRfnorteInput(): Promise<string> {
    return await this.rfnorteInput.getAttribute('value');
  }

  async setRfsurInput(rfsur: string): Promise<void> {
    await this.rfsurInput.sendKeys(rfsur);
  }

  async getRfsurInput(): Promise<string> {
    return await this.rfsurInput.getAttribute('value');
  }

  async setS01Input(s01: string): Promise<void> {
    await this.s01Input.sendKeys(s01);
  }

  async getS01Input(): Promise<string> {
    return await this.s01Input.getAttribute('value');
  }

  async setS02Input(s02: string): Promise<void> {
    await this.s02Input.sendKeys(s02);
  }

  async getS02Input(): Promise<string> {
    return await this.s02Input.getAttribute('value');
  }

  async setS03Input(s03: string): Promise<void> {
    await this.s03Input.sendKeys(s03);
  }

  async getS03Input(): Promise<string> {
    return await this.s03Input.getAttribute('value');
  }

  async setS04Input(s04: string): Promise<void> {
    await this.s04Input.sendKeys(s04);
  }

  async getS04Input(): Promise<string> {
    return await this.s04Input.getAttribute('value');
  }

  async setFechaInput(fecha: string): Promise<void> {
    await this.fechaInput.sendKeys(fecha);
  }

  async getFechaInput(): Promise<string> {
    return await this.fechaInput.getAttribute('value');
  }

  async setUsuarioInput(usuario: string): Promise<void> {
    await this.usuarioInput.sendKeys(usuario);
  }

  async getUsuarioInput(): Promise<string> {
    return await this.usuarioInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class TcManifesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-tcManifes-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-tcManifes'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
