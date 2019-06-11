/********************************************************************************
** Form generated from reading UI file 'mainwindow.ui'
**
** Created by: Qt User Interface Compiler version 5.9.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAINWINDOW_H
#define UI_MAINWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QFrame>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QTableWidget>
#include <QtWidgets/QTextEdit>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MainWindow
{
public:
    QWidget *centralWidget;
    QFrame *mainFrame;
    QTextEdit *ideTextEdit;
    QPushButton *runPushButton;
    QTextEdit *appOutputTextEdit;
    QLabel *AppOutputLabel;
    QTableWidget *metadataTable;
    QPushButton *subirImagenPushButton;
    QPushButton *visualizarImagenPushButton;
    QPushButton *rollbackPushButton;
    QPushButton *commitPushButton;

    void setupUi(QMainWindow *MainWindow)
    {
        if (MainWindow->objectName().isEmpty())
            MainWindow->setObjectName(QStringLiteral("MainWindow"));
        MainWindow->resize(1200, 700);
        centralWidget = new QWidget(MainWindow);
        centralWidget->setObjectName(QStringLiteral("centralWidget"));
        mainFrame = new QFrame(centralWidget);
        mainFrame->setObjectName(QStringLiteral("mainFrame"));
        mainFrame->setGeometry(QRect(0, 0, 1200, 700));
        mainFrame->setFrameShape(QFrame::Panel);
        mainFrame->setFrameShadow(QFrame::Plain);
        mainFrame->setLineWidth(0);
        ideTextEdit = new QTextEdit(mainFrame);
        ideTextEdit->setObjectName(QStringLiteral("ideTextEdit"));
        ideTextEdit->setGeometry(QRect(530, 10, 660, 460));
        runPushButton = new QPushButton(mainFrame);
        runPushButton->setObjectName(QStringLiteral("runPushButton"));
        runPushButton->setGeometry(QRect(530, 480, 80, 22));
        appOutputTextEdit = new QTextEdit(mainFrame);
        appOutputTextEdit->setObjectName(QStringLiteral("appOutputTextEdit"));
        appOutputTextEdit->setGeometry(QRect(530, 530, 661, 161));
        AppOutputLabel = new QLabel(mainFrame);
        AppOutputLabel->setObjectName(QStringLiteral("AppOutputLabel"));
        AppOutputLabel->setGeometry(QRect(530, 510, 111, 16));
        metadataTable = new QTableWidget(mainFrame);
        metadataTable->setObjectName(QStringLiteral("metadataTable"));
        metadataTable->setGeometry(QRect(10, 10, 510, 240));
        subirImagenPushButton = new QPushButton(mainFrame);
        subirImagenPushButton->setObjectName(QStringLiteral("subirImagenPushButton"));
        subirImagenPushButton->setGeometry(QRect(40, 640, 131, 41));
        visualizarImagenPushButton = new QPushButton(mainFrame);
        visualizarImagenPushButton->setObjectName(QStringLiteral("visualizarImagenPushButton"));
        visualizarImagenPushButton->setGeometry(QRect(40, 260, 191, 41));
        rollbackPushButton = new QPushButton(mainFrame);
        rollbackPushButton->setObjectName(QStringLiteral("rollbackPushButton"));
        rollbackPushButton->setGeometry(QRect(710, 480, 80, 22));
        commitPushButton = new QPushButton(mainFrame);
        commitPushButton->setObjectName(QStringLiteral("commitPushButton"));
        commitPushButton->setGeometry(QRect(620, 480, 80, 22));
        MainWindow->setCentralWidget(centralWidget);

        retranslateUi(MainWindow);

        QMetaObject::connectSlotsByName(MainWindow);
    } // setupUi

    void retranslateUi(QMainWindow *MainWindow)
    {
        MainWindow->setWindowTitle(QApplication::translate("MainWindow", "MainWindow", Q_NULLPTR));
        runPushButton->setText(QApplication::translate("MainWindow", "Run", Q_NULLPTR));
        AppOutputLabel->setText(QApplication::translate("MainWindow", "Application Output", Q_NULLPTR));
        subirImagenPushButton->setText(QApplication::translate("MainWindow", "Subir Imagen/Galer\303\255a", Q_NULLPTR));
        visualizarImagenPushButton->setText(QApplication::translate("MainWindow", "Visualizar imagen seleccionada", Q_NULLPTR));
        rollbackPushButton->setText(QApplication::translate("MainWindow", "Rollback", Q_NULLPTR));
        commitPushButton->setText(QApplication::translate("MainWindow", "Commit", Q_NULLPTR));
    } // retranslateUi

};

namespace Ui {
    class MainWindow: public Ui_MainWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINWINDOW_H
