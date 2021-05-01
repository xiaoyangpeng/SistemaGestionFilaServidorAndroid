package ConectarConMovil

import java.net.ServerSocket
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea

class ServidorMovil : JFrame(),Runnable {

    var textPane=	JTextArea()

    var scrollPane=JScrollPane()


    init {

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        setBounds(500, 500, 500, 500)

        contentPane.layout = null


        scrollPane.setBounds(10, 10, 445, 443)

        contentPane.add(scrollPane)

        scrollPane.setViewportView(textPane)


    }


    override fun run() {

        val servidor=ServerSocket(8888)


        while(true){

            val misocket=servidor.accept()

            var controladorServidor = ControladorServidor(misocket, textPane)

            textPane.append("\n"+"incorporado")

            controladorServidor.start()

        }


    }


}


fun main() {

    var frame=ServidorMovil()

    frame.isVisible = true

    var hilo=Thread(frame)

    hilo.start()


}