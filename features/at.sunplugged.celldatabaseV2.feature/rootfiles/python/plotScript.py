'''
Created on 09.09.2017

@author: Jascha Riedel
'''

import sys
from plotting import PlotHelper

if __name__ == '__main__':
    
    print("PlotScript loaded...")
    
    plotHelper = PlotHelper()
    
    
    print("Loading Json File...")
    plotHelper.addJsonFile(open(sys.argv[1], "r"))
    #plotHelper.addJsonFile(open("plotCellResults.json", "r"))
    
    print("Plotting...")
    
    plotHelper.showPlot()
    
    print("Exiting PlotScript...")
    sys.exit(0)