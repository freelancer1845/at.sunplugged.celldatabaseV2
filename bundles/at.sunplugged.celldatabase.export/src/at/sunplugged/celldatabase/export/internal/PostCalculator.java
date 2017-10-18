package at.sunplugged.celldatabase.export.internal;

import datamodel.CellResult;

public interface PostCalculator {
  double calc(double value, CellResult result);

}
