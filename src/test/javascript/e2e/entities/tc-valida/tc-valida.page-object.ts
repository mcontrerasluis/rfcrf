import { element, by, ElementFinder } from 'protractor';

export class TcValidaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-tc-valida div table .btn-danger'));
  title = element.all(by.css('jhi-tc-valida div h2#page-heading span')).first();
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

export class TcValidaUpdatePage {
  pageTitle = element(by.id('jhi-tc-valida-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  claveInput = element(by.id('field_clave'));
  descripcionInput = element(by.id('field_descripcion'));
  moralInput = element(by.id('field_moral'));
  fisicaInput = element(by.id('field_fisica'));
  isrInput = element(by.id('field_isr'));
  ivaInput = element(by.id('field_iva'));
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

export class TcValidaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-tcValida-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-tcValida'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
