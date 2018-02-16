'''
Created on 30.08.2017

@author: Jascha Riedel
'''

import sys
import evaluation
import os
import codecs, json
import numpy as np
import scipy
import matplotlib.pyplot as plt
from evaluation.CellDataObject import CellDataObject

def uiMethod(lightData, darkData, area, powerInput):
    '''
        Evaluates Parameters using a 5-grade Polynomial fitting the UI Data
    '''
    print(area, powerInput)
    lightUIFit = np.poly1d(np.polyfit(lightData[:,0], lightData[:,1], 5))
    powerUIFit = np.poly1d(np.polyfit(lightData[:,0], lightData[:,1] * lightData[:,0], 5))
    darkUIFit = np.poly1d(np.polyfit(darkData[:,0], darkData[:,1], 5))

    dataResult = {}
    
    dataResult['lightUIFitCoefficients'] = lightUIFit.coeffs.tolist();
    dataResult['darkUIFitCoefficients'] = darkUIFit.coeffs.tolist();
    dataResult['powerUIFitCoefficients'] = powerUIFit.coeffs.tolist();
        
    dataResult['Voc'] = scipy.optimize.brenth(lightUIFit, lightData[0,0], lightData[-1,0])
    print('Voc calculated', np.min(lightData[:,0]))
    dataResult['Isc'] = lightUIFit(0)
    dataResult['Area'] = area
    dataResult['PowerInput'] = powerInput
    dataResult['Rp'] = 1 / np.polyder(lightUIFit)(0)
    dataResult['Rs'] = 1 / np.polyder(lightUIFit)(dataResult['Voc'])
    mppV = scipy.optimize.fmin(lambda x: powerUIFit(x) * -1, 0, disp =False)[0]
    mppI = lightUIFit(mppV)
    mpp = mppV * mppI
    dataResult['MaximumPowerV'] = mppV
    dataResult['MaximumPowerI'] = mppI
    dataResult['RsDark'] = 1/np.polyder(darkUIFit)(0.9 * darkData[-1,0])
    dataResult['RpDark'] = 1/np.polyder(darkUIFit)(0)
    dataResult['FF'] = abs(mpp / dataResult['Voc'] / dataResult['Isc'] * 100)
    dataResult['Eff'] = abs((mpp / area) / powerInput)  
    print(dataResult)
    return dataResult
    

if __name__ == '__main__':
    args = sys.argv[1:]
    
    jsonFiles = args[0]
    
    files = json.load(open(jsonFiles))
    
    baseDir = args[1]
    
    results = []
    
    for file in files:
        darkDataFileName = file['absolutPathDark']
        lightDataFileName = file['absolutPathLight']
        area = float(file['area'])
        powerInput = float(file['powerInput'])
        lightData = evaluation.fileIO.readLabViewFile(lightDataFileName)
        darkData = evaluation.fileIO.readLabViewFile(darkDataFileName)
        
        try:
            dataResultDict =  uiMethod(lightData, darkData, area, powerInput)
            dataResultDict['ID'] = file['name']
            dataResultDict['lightData'] = lightData.tolist();
            dataResultDict['darkData'] = darkData.tolist();
            results.append(dataResultDict)
        except Exception as e:
            raise e
        
        '''
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
          '''
    json.dump(results, codecs.open(baseDir + "\\result.json", 'w', encoding='utf-8'), sort_keys=True, indent=4)
    sys.exit(0)
    

    
    
    
    
