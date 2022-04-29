from PyQt6.QtWidgets import QApplication, QWidget, QLabel, QHBoxLayout, QVBoxLayout
from PyQt6.QtWidgets import QPushButton, QDial, QLineEdit, QLCDNumber
from PyQt6.QtCore import Qt, QObject, pyqtSignal
from PyQt6.QtGui import QIntValidator
import typing
import sys

class ADial(QWidget):
    valueChanged = pyqtSignal()

    def __init__(self,
            orientation: Qt.Orientation, texte: str,
            minMax: typing.Tuple[int, int],
            defVal: int):


        super().__init__()
        # attributs
        self._valeurDefaut = defVal
        self._minMax = minMax
        self.__value = defVal

        # layout
        if orientation == Qt.Orientation.Vertical:
            self._layout = QVBoxLayout()
            self.setLayout(self._layout)
        else:
            self._layout = QHBoxLayout()
            self.setLayout(self._layout)

        # label
        self._label = QLabel(texte)
        self._layout.addWidget(self._label)
        # entrée texte
        self._entree = QLineEdit()
        self._entree.setValidator(QIntValidator())
        self._entree.setText(str(self._valeurDefaut))
        self._layout.addWidget(self._entree)
        # dial
        self._dial = QDial()
        self._dial.setMinimum(self._minMax[0])
        self._dial.setMaximum(self._minMax[1])
        self._dial.setValue(self._valeurDefaut)
        self._layout.addWidget(self._dial)
        # reset
        self._reset = QPushButton("reset")
        self._layout.addWidget(self._reset)
        # partie : signal #
        self._dial.valueChanged.connect(self.dialChanged)
        self._reset.clicked.connect(self.reset)
        self._entree.returnPressed.connect(self.entree)

    # dial changed
    def dialChanged(self):
        val = self._dial.value()
        self._entree.setText(str(val))
        self.__value = val
        self.emitValueChanged()
    # reset
    def reset(self):
        self._dial.setValue(self._valeurDefaut)
        self.__value = self._valeurDefaut
        self._entree.setText(str(self._valeurDefaut))
        self.emitValueChanged()
    # entree
    def entree(self):
        val = int(self._entree.text())
        self.__value = val
        self._dial.setValue(val)
        self.emitValueChanged()

    def value(self): return self.__value

    def emitValueChanged(self):
        self.valueChanged.emit()


class myApp(QWidget):
    def __init__(self):
        super().__init__()
        self.setWindowTitle('testing ADial')
        self._layout = QHBoxLayout()
        self.setLayout(self._layout)
        self.lcd = QLCDNumber(3)
        self.lcd.setMinimumWidth(400)
        self._layout.addWidget(self.lcd)
        self.dial0 = ADial(Qt.Orientation.Vertical, 'une valeur', (0, 9), 0)
        self.dial0.valueChanged.connect(self.msg)
        self._layout.addWidget(self.dial0)
        self.dial1 = ADial(Qt.Orientation.Vertical, 'une valeur', (0, 9), 0)
        self.dial1.valueChanged.connect(self.msg)
        self._layout.addWidget(self.dial1)
        self.dial2 = ADial(Qt.Orientation.Vertical, 'une valeur', (0, 9), 0)
        self.dial2.valueChanged.connect(self.msg)
        self._layout.addWidget(self.dial2)
        self.show()

    def msg(self):
        n = 100*self.dial0.value() + 10*self.dial1.value() + self.dial2.value()
        self.lcd.display(n)

if __name__ == "__main__":
    print(f' --- main --- ')
    # création d'une QApplication
    app = QApplication(sys.argv)
    # creation d'un widget
    f = myApp()
    # lancement de l'application
    sys.exit(app.exec())
