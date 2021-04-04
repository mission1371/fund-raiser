import { browser, logging } from 'protractor';
import { AppPage } from './app.po';

describe('workspace-project Fund-Raiser', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should have catalog', async () => {
    await page.navigateTo();
    expect(await page.hasCatalog()).toBeTrue();
  });

  afterEach(async () => {
    // Assert that there are no errors emitted from the browser
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    } as logging.Entry));
  });
});
