'''
Created on 21.07.2017

@author: Jascha Riedel
'''

import numpy as np
import matplotlib.pyplot as plt
import scipy.optimize
from scipy.interpolate import interp1d

def findMpp(data):
    '''
        Finds Mpp by interpolating U*I in a cubic way.
        Parameters:
            data: 2-D Array containing U-I data
        Returns:
            result: MppU, MppI, Mpp Power
    '''
    data = data[data[:,0].argsort()]
    uniqueData = np.array(data)
    for i in range(0, uniqueData[:,0].size):
        if (i < (uniqueData[:,0].size -2)):
            if uniqueData[i,0] == uniqueData[i + 1,0]:
                uniqueData[i,0] = uniqueData[i,0] - 1e-8
    
    iF = interp1d(uniqueData[:,0],uniqueData[:,1] * uniqueData[:,0], 'cubic')
    x = scipy.optimize.fmin(lambda x: iF(x) * -1, 0, disp=False)
    
    uIInterp = interp1d(uniqueData[:,0], uniqueData[:,1], 'cubic')
    uIPolyFit = np.poly1d(np.polyfit(uniqueData[:,0], uniqueData[:,1], 5))
    polyfit = np.poly1d(np.polyfit(uniqueData[:,0], uniqueData[:,1] * uniqueData[:,0], 5))
    x = scipy.optimize.fmin(lambda x: polyfit(x) * -1, 0, disp=False)
    plt.plot(data[:,0], data[:,1], label='real Data')
    plt.plot(data[:,0], uIPolyFit(data[:,0]), label = 'UI Poly Fit')
    plt.plot(data[:,0], data[:,0] * data[:,1], label='real power values')
    plt.plot(data[:,0], iF(data[:,0]), label = 'interpolated power values')
    plt.plot(data[:,0], polyfit(data[:,0]), label= "polyinterpolated power values")
    plt.legend()
    plt.show()
    
    return np.array([x[0], uIInterp(x[0]),  iF(x)[0]])