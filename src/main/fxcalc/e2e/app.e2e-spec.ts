import { FxCalcPage } from './app.po';

describe('fx-calc App', function() {
  let page: FxCalcPage;

  beforeEach(() => {
    page = new FxCalcPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
