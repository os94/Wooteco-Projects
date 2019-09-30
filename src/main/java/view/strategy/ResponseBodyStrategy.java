package view.strategy;

import view.ModelAndView;

public interface ResponseBodyStrategy {
    byte[] render(ModelAndView modelAndView);
}
