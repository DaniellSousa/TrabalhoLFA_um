package um.trabalho.lfa.trabalholfa_um

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var alfabeto = ArrayList<String>()
    var estados = ArrayList<Integer>()
    var estadoinicial: Int = 0
    var estadosFinais = ArrayList<Integer>()
    var funcoes = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnValidar.setOnClickListener {

            if (etAlfabeto.text.toString().trim().length == 0) {
                this.showMessage(0, R.string.msg_alfabeto_vazio, 0, false)
            }else if (etAlfabeto.text.toString().trim()[etAlfabeto.length()-1].equals(",")) {
                this.showMessage(0, R.string.msg_alfabeto_invalido_virgula, 0, false)
            }else if (etEstados.text.toString().length == 0) {
                this.showMessage(0, R.string.msg_estados_vazios, 0, false)
            } else if (etEstadoInicial.text.toString().length == 0) {
                this.showMessage(0, R.string.msg_estado_inicial_vazio, 0, false)
            }else if (etEstadosFinais.text.toString().length == 0) {
                this.showMessage(0, R.string.msg_estados_finais_vazios, 0, false)
            }else if (etEstadosFinais.text.toString().trim()[etAlfabeto.length()-1].equals(",")) {
                this.showMessage(0, R.string.msg_estados_finais_invalidos, 0, false)
            }else if (etFuncoes.text.toString().trim().length == 0) {
                this.showMessage(0, R.string.msg_funcoes_vazias, 0, false)
            }else {
                this.preencherValores()
            }

        }
    }

    fun preencherValores() {
        organizarAlfabeto()
        organizarEstados()
        organizarEstadoInicial()
        organizarEstadosFinais()
        organizarFuncoes()
    }

    fun organizarAlfabeto() {

    }

    fun organizarEstados() {

    }

    fun organizarEstadoInicial() {

    }

    fun organizarEstadosFinais() {

    }

    fun organizarFuncoes() {

    }

    fun showMessage(title: Int, message: Int, image: Int, isFinish: Boolean) {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setTitle(title)
        dialogBuilder.setMessage(message)
        dialogBuilder.setCancelable(false)
        dialogBuilder.setIcon(image)

        dialogBuilder.setPositiveButton("OK",
                DialogInterface.OnClickListener {
                    dialog, which ->

                    dialog.dismiss()

                    if (isFinish)
                        finish()

                })

        dialogBuilder.create().show()
    }
}
