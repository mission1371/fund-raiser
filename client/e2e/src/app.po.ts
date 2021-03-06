import { browser, by, element } from 'protractor';

export class AppPage {
  async navigateTo(): Promise<unknown> {
    return browser.get(browser.baseUrl);
  }

  async hasCatalog(): Promise<boolean> {
    return element(by.css('app-root mat-accordion')).isPresent();
  }
}
