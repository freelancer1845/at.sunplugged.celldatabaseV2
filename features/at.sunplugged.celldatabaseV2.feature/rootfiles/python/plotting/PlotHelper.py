'''
Created on 09.09.2017

@author: Jascha Riedel
'''

import json
from evaluation import CellDataObject
import numpy as np
import matplotlib.pyplot as plt


class PlotHelper():
    '''
    Use this class to help plotting json formatted data with various options.
    '''

    


    def __init__(self):
        '''
        Constructor
        '''
        
        self.plotObjects = []
        
        
   
    
    def addJsonFile(self, filePath):
        eObjects = json.load(filePath)
        for eObject in eObjects:
            if 'area' in eObject:
                dataSet = self._desearilizeDataSet(eObject)
                self.plotObjects.append(dataSet)
            else:
                cellData = self._desearilizeCellResult(eObject)
                self.plotObjects.append(cellData)
    
    def showPlot(self):
        self._simpleUIDataPlot()
    
    
    def _simpleUIDataPlot(self):
        if (len(self.plotObjects) == 0):
            return
        ymin, ymax = self._findYMaxAndMin(self.plotObjects)
        
        for cellData in self.plotObjects:
            data = cellData.data;
            plt.plot(data[:,0], -1 * data[:,1], label=cellData.Id)
        
        plt.ylim(ymin, ymax)
        plt.grid()
        plt.legend(fontsize=6)
        plt.axhline(y=0, color='k')
        plt.axvline(x=0, color='k')
        
        plt.show()
        
    def _findYMaxAndMin(self, dataObjects):
        ymin = 0
        ymax = 0
        for cellData in dataObjects:
            yminT = min(cellData.data[:,1]) - 0.1 * abs(min(cellData.data[:,1]))
            ymaxT = max(cellData.data[:,1]) + 0.1 * abs(max(cellData.data[:,1]))
         
            if (yminT < ymin):
                ymin = yminT
            if (ymaxT > ymax):
                ymax = ymaxT
                
        return (ymin, ymax)
    
    def _desearilizeCellResult(self, cellResult):
        cellData = CellDataObject()
        cellData.Id = cellResult['name']
        cellData.Voc = cellResult['openCircuitVoltage']
        cellData.Isc = cellResult['shortCircuitCurrent']
        cellData.Rp = cellResult['parallelResistance']
        cellData.MppI = cellResult['maximumPowerCurrent']
        cellData.MppU = cellResult['maximumPowerVoltage']
        cellData.FF = cellResult['fillFactor']
        cellData.Eff = cellResult['efficiency']
        dataSet = cellResult['lightMeasurementDataSet']
        cellData.Area = dataSet['area']
        cellData.powerInput = dataSet['powerInput']
        dataArray = np.array(dataSet['data'])
        dataArrayFlat = []
        for point in dataArray:
            dataArrayFlat.append([point['voltage'], point['current']])
        cellData.data = np.array(dataArrayFlat)
        
        return cellData
        
    def _desearilizeDataSet(self, eObject):
        dataSet = CellDataObject()

        dataSet.Id = eObject['name']
        dataArray = np.array(eObject['data'])
        dataArrayFlat = []
        for point in dataArray:
            dataArrayFlat.append([point['voltage'], point['current']])
        dataSet.data = np.array(dataArrayFlat)
        return dataSet

    
    