package at.sunplugged.celldatabaseV2.export.internal;

import datamodel.CellResult;

public interface PostCalculator {
  double calc(double value, CellResult result);

}