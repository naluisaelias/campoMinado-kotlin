package visao

import modelo.Campo
import modelo.CampoEvento
import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.SwingUtilities

// MUDANÇA --> PALETA DE CORES
private val COR_BG_NORMAL = Color(255,182,193)
private val COR_BG_MARCACAO = Color(199,21,133)
private val COR_BG_EXPLOSAO = Color(220,20,60)
private val COR_TXT_VERDE = Color(255,20,147)

class BotaoCampo(private val campo: Campo) : JButton() {

    init {
        font = font.deriveFont(Font.ITALIC)
        background = COR_BG_NORMAL
        isOpaque = true
        border = BorderFactory.createBevelBorder(0)
        addMouseListener(MouseCliqueListener(campo, { it.abrir() }, { it.alterarMarcacao() }))

        campo.onEvento(this::aplicarEstilo)
    }

    private fun aplicarEstilo(campo: Campo, evento: CampoEvento) {
        when(evento) {
            CampoEvento.EXPLOSAO -> aplicarEstiloExplodido()
            CampoEvento.ABERTURA -> aplicarEstiloAberto()
            CampoEvento.MARCACAO -> aplicarEstiloMarcado()
            else -> aplicarEstiloPadrao()
        }

        SwingUtilities.invokeLater {
            repaint()
            validate()
        }
    }

    private fun aplicarEstiloExplodido() {
        background = COR_BG_EXPLOSAO
        text = "\uD83D\uDCA3"
    }

    private fun aplicarEstiloAberto() {
        background = COR_BG_NORMAL
        border = BorderFactory.createLineBorder(Color.GRAY)

        foreground = when (campo.qtdeVizinhosMinados) {
            1 -> COR_TXT_VERDE
            2 -> Color.BLUE
            3 -> Color.PINK
            4, 5, 6 -> Color.RED
            else -> Color.PINK
        }

        text = if (campo.qtdeVizinhosMinados > 0) campo.qtdeVizinhosMinados.toString() else ""
    }

    private fun aplicarEstiloMarcado() {
        background = COR_BG_MARCACAO
        foreground = Color.BLACK
        text = "⛳"
    }

    private fun aplicarEstiloPadrao() {
        background = COR_BG_NORMAL
        border = BorderFactory.createBevelBorder(0)
        text = ""
    }

}