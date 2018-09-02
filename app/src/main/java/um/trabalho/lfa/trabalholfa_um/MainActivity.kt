package um.trabalho.lfa.trabalholfa_um

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var alfabeto = ArrayList<String>()
    var estados = ArrayList<Int>()
    var estadoinicial: Int = 0
    var estadosFinais = ArrayList<Int>()
    var funcoes = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnValidar.setOnClickListener {

            if (etAlfabeto.text.toString().trim().length == 0) {
                this.showMessage(R.string.msg_alfabeto_vazio, 0, false)
            }else if (etAlfabeto.text.toString().trim()[etAlfabeto.length()-1].equals(",")) {
                this.showMessage(R.string.msg_alfabeto_invalido_virgula, 0, false)
            }else if (etEstados.text.toString().length == 0) {
                this.showMessage(R.string.msg_estados_vazios, 0, false)
            } else if (etEstadoInicial.text.toString().length == 0) {
                this.showMessage(R.string.msg_estado_inicial_vazio, 0, false)
            }else if (etEstadosFinais.text.toString().trim().length == 0) {
                this.showMessage(R.string.msg_estados_finais_vazios, 0, false)
            }else if (etEstadosFinais.text.toString().trim()[etEstadosFinais.length()-1].equals(",")) {
                this.showMessage( R.string.msg_estados_finais_invalidos, 0, false)
            }else if (etFuncoes.text.toString().trim().length == 0) {
                this.showMessage(R.string.msg_funcoes_vazias, 0, false)
            }else {
                this.preencherValores()
            }

        }
    }

    fun preencherValores() {
        organizarAlfabeto(etAlfabeto.text.toString().trim())
        organizarEstados(etEstados.text.toString().trim().toInt())
        organizarEstadoInicial()
        organizarEstadosFinais(etEstadosFinais.text.toString().trim())
        organizarFuncoes(etFuncoes.text.toString().trim())

//        printAll()
    }

    fun organizarAlfabeto(alfa: String) {
        var i = 0

        while(i<alfa.length) {

            if (alfa.get(i) != ',') {
                this.alfabeto.add(alfa.get(i).toString())
            }

            i+=1
        }

    }

    fun organizarEstados(qtdEstados: Int) {
        var i: Int = 0

        while (i < qtdEstados) {
            this.estados.add(i)
            i+=1
        }

    }

    fun organizarEstadoInicial() {
        this.estadoinicial = 0
    }

    fun organizarEstadosFinais(estados: String) {
        var i = 0

        while(i<estados.length) {

            if (estados.get(i) != ',') {
                this.estadosFinais.add(estados.get(i).toString().toInt())
            }

            i+=1
        }
    }

    fun organizarFuncoes(funcs: String) {
        var i = 0
        var pre = ""
        var inn = ""
        var pos = ""

        while (i<funcs.length) {

            if (funcs.get(i) == ';') {

                pre = funcs.get(i-6).toString()
                inn = funcs.get(i-4).toString()
                pos = funcs.get(i-2).toString()

                this.funcoes.add(pre+inn+pos)
            }

            i += 1
        }
    }

    fun printAll() {
        Log.e("Alfabeto", this.alfabeto.toString())
        Log.e("Estados", this.estados.toString())
        Log.e("Estado inicial", this.estadoinicial.toString())
        Log.e("Estados finais", this.estadosFinais.toString())
        Log.e("Funções", this.funcoes.toString())
    }

    fun showMessage(message: Int, image: Int, isFinish: Boolean) {
        val dialogBuilder = AlertDialog.Builder(this)

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
