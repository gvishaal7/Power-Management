# Power-Management
Power Management Module of an electric car.

Modules used: Cell, Battery, Baseline, Car, User

Cell module is used to store the individual capacity of each cell present in a battery in watts(w).

Battery module contains every detail of the battery. The total capacity of the battery is the sum of the capacity of each individual cell present in the battery. The decay of the battery is depended on the number of passengers and the temperature of the battery. The tripCharge array stores the energy consumed for every 100KM. The discharge member helps in determining the number of battery cycles consumed.
The following assumptions were made for the class,
1) The number of cells in a given battery is fixed.
2) The capacity of each cell varies between 11W and 16W.
3) The temperature of the battery is either one of the following, hot(h), normal(n) and cold(c).
4) The factory set baselines for the battery for every 100KM is set high so that over the course of time, the running average is adjusted gradually.
5) There is no leak or overflow of charges while charging.
6) 20% and below is considered as low battery.

Baseline module has the following factory defined baselines:
1) One passenger: 70kW
2) Two passenger: 85kW
3) Three passenger: 100kW
4) Four passenger: 120kW

Car module contains the details about the car, total distance travalled, type of battery and the currentTrip array resets every 100KM travlled by the user.

User module contains the list of cars owned by the user.

Calculations are made in watts(W) and later converted to kilowatt(kW) before storing and/or displaying.

Double is used over float in many places so that the values are more precise.
