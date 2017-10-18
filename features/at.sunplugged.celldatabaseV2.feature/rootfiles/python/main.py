'''
Created on 30.08.2017

@author: Jascha Riedel
'''

import sys
import evaluation
import os
import codecs, json
from evaluation.CellDataObject import CellDataObject

if __name__ == '__main__':
    args = sys.argv[1:]
    
    darkDataFileName = args[0]
    lightDataFileName = args[1]
    area = float(args[2])
    powerInput = float(args[3])
    baseDir = args[4]
    
    lightData = evaluation.fileIO.readLabViewFile(lightDataFileName)
    darkData = evaluation.fileIO.readLabViewFile(darkDataFileName)
    dataResult =  CellDataObject.createFromData(os.path.basename(lightDataFileName), lightData, darkData, area, powerInput)
    
    dataResultDict = {
                    'ID': dataResult.Id,
                    'Voc': dataResult.Voc,
                    'Isc': dataResult.Isc,
                    'Area': dataResult.Area,
                    'PowerInput': dataResult.powerInput,
                    'FF': dataResult.FF,
                    'Eff': dataResult.Eff,
                    'Rp': dataResult.Rp,
                    'RpDark': dataResult.RpDark,
                    'Rs':dataResult.Rs,
                    'RsDark': dataResult.RsDark,
                    'MaximumPowerV': dataResult.MppU,
                    'MaximumPowerI': dataResult.MppI
                      }
    print(args[1] + "data.json")
    json.dump({'lightData': lightData.tolist(), 'darkData': darkData.tolist()}, codecs.open(baseDir + "\\data.json", 'w', encoding='utf-8'), sort_keys=True, indent=4)
    json.dump(dataResultDict, codecs.open(baseDir + "\\result.json", 'w', encoding='utf-8'), sort_keys=True, indent=4)
    print(sys.argv[0])
    sys.exit(0)
    
