import { render } from '@testing-library/react';
import Home from './index.tsx';

describe('Homepage component', () => {
  it('should render some content', () => {
    const { container } = render(<Home />);
    expect(container).not.toBeEmptyDOMElement();
  });
});
