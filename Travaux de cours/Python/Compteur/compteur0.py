import sys
from PyQt6.QtWidgets import QApplication
from PyQt6.QtWidgets import QVBoxLayout
from PyQt6.QtWidgets import QWidget, QLabel, QPushButton, QDial, QLineEdit
from PyQt6.QtGui import QIntValidator
from PyQt6.QtCore import pyqtSignal


class AddDial(QWidget):
    valueChange : pyqtSignal = pyqtSignal(int)

    def __init__(self, defVal : int = 0, maxminV: tuple[int, int] = [0, 10]):
        super().__init__()
        self.setWindowTitle('Dial Compteur')
        self.__compteurV : int = defVal
        self.__maxV : int= maxminV[1]
        self.__minV :int = maxminV[0]

        self.__vlayout = QVBoxLayout()
        self.setLayout(self.__vlayout)

        self.__label : QLabel = QLabel()
        self.__label.setText('une valeur')
        self.__vlayout.addWidget(self.__label)

        self.__inputLine = QLineEdit()
        self.__inputLine.setValidator(QIntValidator())
        self.__inputLine.setText(str(self.__compteurV))
        self.__vlayout.addWidget(self.__inputLine)

        self.__dial = QDial()
        self.__dial.setMaximum(self.__maxV)
        self.__dial.setMinimum(self.__minV)
        self.__dial.setValue(self.__compteurV)
        self.__vlayout.addWidget(self.__dial)

        self.__btReset = QPushButton('Reset')
        self.__vlayout.addWidget(self.__btReset)

        self.__btHelp = QPushButton('Help')
        self.__vlayout.addWidget(self.__btHelp)

        self.__helpText = QLabel('c\'est quoi ? ')
        self.__helpText.setHidden(True)
        self.__vlayout.addWidget(self.__helpText)


        self.__btHelp.clicked.connect(self.callbackBtHelp)
        self.__inputLine.editingFinished.connect(self.callbackIL)
        self.__dial.valueChanged.connect(self.callbackDial)
        self.__btReset.clicked.connect(self.callbackReset)

        # self.__addDial.valueChange.connect(self.XXXX)

        self.show()

    def callbackBT(self):
        self.__compteurV += 1
        self.__label.setText(str(self.__compteurV))

    def callbackBtHelp(self):
        if self.__helpText.isHidden():
            self.__helpText.setHidden(False)
        else:
            self.__helpText.setHidden(True)

    def callbackIL(self):
        self.__compteurV = int(self.__inputLine.text())
        self.__dial.setValue(self.__compteurV)
        self.emitSignalValueChange(self.__compteurV)

    def callbackDial(self):
        self.__compteurV = int(self.__dial.value())
        self.__inputLine.setText(str(self.__compteurV))
        self.emitSignalValueChange(self.__compteurV)


    def callbackReset(self):
        self.__compteurV = 0
        self.__inputLine.setText(str(self.__compteurV))
        self.__dial.setValue(self.__compteurV)

    def emitSignalValueChange(self, val : int):
        print('signal change')
        self.valueChange.emit(val)


if __name__ == "__main__":
    app = QApplication(sys.argv)
    cp = AddDial()
    sys.exit(app.exec())






