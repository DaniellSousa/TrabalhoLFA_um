package um.trabalho.lfa.trabalholfa_um

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast

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

            saveFieldStates()

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

        btnVerificarInput.setOnClickListener {

            if () {

            }

        }

        this.getAndSetStateValuesFields()
    }

    fun saveFieldStates() {
        val ph = PreferencesHelper(this)
        ph.saveObject("alfabeto", etAlfabeto.text.toString().trim())
        ph.saveObject("estados", etEstados.text.toString().trim())
        ph.saveObject("estadoInicial", etEstadoInicial.text.toString().trim())
        ph.saveObject("estadosFinais", etEstadosFinais.text.toString().trim())
        ph.saveObject("funcoes", etFuncoes.text.toString().trim())
    }

    fun getAndSetStateValuesFields() {
        val ph = PreferencesHelper(this)
        etAlfabeto.text = Editable.Factory.getInstance().newEditable(ph.getObject("alfabeto"))
        etEstados.text = Editable.Factory.getInstance().newEditable(ph.getObject("estados"))
        etEstadoInicial.text = Editable.Factory.getInstance().newEditable(ph.getObject("estadoInicial"))
        etEstadosFinais.text = Editable.Factory.getInstance().newEditable(ph.getObject("estadosFinais"))
        etFuncoes.text = Editable.Factory.getInstance().newEditable(ph.getObject("funcoes"))
    }

    fun preencherValores() {
        organizarAlfabeto(etAlfabeto.text.toString().trim())
        organizarEstados(etEstados.text.toString().trim().toInt())
        organizarEstadoInicial()
        organizarEstadosFinais(etEstadosFinais.text.toString().trim())
        organizarFuncoes(etFuncoes.text.toString().trim())

        Toast.makeText(this, "Autômato válido!", Toast.LENGTH_LONG).show()
        setVisibilityBtnInput()

//        printAll()
    }

    fun setVisibilityBtnInput() {
        btnVerificarInput.visibility = View.VISIBLE
        layoutEntradas.visibility = View.VISIBLE
    }

    fun setInvisibleBtninput() {
        btnVerificarInput.visibility = View.GONE
        layoutEntradas.visibility = View.GONE
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
