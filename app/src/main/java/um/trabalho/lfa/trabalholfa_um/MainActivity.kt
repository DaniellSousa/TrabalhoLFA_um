package um.trabalho.lfa.trabalholfa_um

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
                this.setInvisibleBtninput()
            }else if (etAlfabeto.text.toString().trim()[etAlfabeto.length()-1].equals(",")) {
                this.showMessage(R.string.msg_alfabeto_invalido_virgula, 0, false)
                this.setInvisibleBtninput()
            }else if (etEstados.text.toString().length == 0) {
                this.showMessage(R.string.msg_estados_vazios, 0, false)
                this.setInvisibleBtninput()
            }else if (etEstadoInicial.text.toString().length == 0) {
                this.showMessage(R.string.msg_estado_inicial_vazio, 0, false)
                this.setInvisibleBtninput()
            }else if (etEstadosFinais.text.toString().trim().length == 0) {
                this.showMessage(R.string.msg_estados_finais_vazios, 0, false)
                this.setInvisibleBtninput()
            }else if (etEstadosFinais.text.toString().trim()[etEstadosFinais.length()-1].equals(",")) {
                this.showMessage( R.string.msg_estados_finais_invalidos, 0, false)
                this.setInvisibleBtninput()
            }else if (etFuncoes.text.toString().trim().length == 0) {
                this.showMessage(R.string.msg_funcoes_vazias, 0, false)
                this.setInvisibleBtninput()
            }else {
                this.preencherValores()
            }

        }

        btnVerificarInput.setOnClickListener {

            if (etPalavraEntrada.text.toString().trim().length == 0) {
                this.showMessage(R.string.msg_input_vazio, 0, false)
            }else {
                checkInput(etPalavraEntrada.text.toString().trim())
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

        printAll()
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
        Log.i("teste Alfabeto", this.alfabeto.toString())
        Log.i("teste Estados", this.estados.toString())
        Log.i("teste Estado inicial", this.estadoinicial.toString())
        Log.i("teste Estados finais", this.estadosFinais.toString())
        Log.i("teste Funções", this.funcoes.toString())
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

    fun checkInput(input: String) {
        var i = 0

        var itemAnterior = ""
        var funcoesAnterior = ArrayList<String>()

        var itemAtual = ""
        var funcoesAtual = ArrayList<String>()

        var itemposterior = ""
        var funcoesPosterior = ArrayList<String>()

        var achouInicial: Boolean = false
        var automatoValido: Boolean = false

        var sair: Boolean = false

        while (i<input.length) {

            if (i > 0) {
                itemAnterior = input.get(i-1).toString()
                funcoesAnterior = getFuncoesItem(itemAtual)
            }

            itemAtual = input.get(i).toString()
            funcoesAtual = getFuncoesItem(itemAtual)

            if (i < (input.length-1)) {
                itemposterior = input.get(i+1).toString()
                funcoesPosterior = getFuncoesItem(itemposterior)
            }else {
                itemposterior = ""
            }

            var j = 0

            if (achouInicial == false) {
                while (j < funcoesAtual.count()) {

                    if (funcoesAtual.get(j).get(0).toString() == "0") {
                        achouInicial = true
                    }

                    if ((j == (funcoesAtual.count()-1)) && (achouInicial == false)) {
                        sair = true
                        automatoValido = false
                    }

                    j += 1
                }
            }

            if (sair) {
                break
            }

            j = 0
            var sair2: Boolean = false
            while(j < funcoesAtual.count()) {

                var z = 0
                while (z < funcoesPosterior.count()) {
                    if (funcoesAtual.get(j).get(0) == funcoesPosterior.get(z).get(0)
                    && (!transicoesDistantes(funcoesAtual.get(j), funcoesPosterior.get(z)))
                    && funcoesAtual.get(j).get(2) != funcoesAtual.get(j).get(0)) {
                        automatoValido = true
                        sair2 = true
                        break
                    }else if (funcoesAtual.get(j).get(2) == funcoesPosterior.get(z).get(0)
                            && funcoesPosterior.get(z).get(0) == funcoesPosterior.get(z).get(2)
                            && (!transicoesDistantes(funcoesAtual.get(j), funcoesPosterior.get(z)))) {
                        automatoValido = true
                        sair2 = true
                        break
                    }else if (itemAnterior != "") {

                        if ((funcoesAtual.get(j).get(2) == funcoesPosterior.get(z).get(0)
                                && funcoesPosterior.get(z).get(0) != funcoesPosterior.get(z).get(2)
                                && funcoesAnterior.get(j).get(0) == funcoesAnterior.get(j).get(2)
                                && (!transicoesDistantes(funcoesAtual.get(j), funcoesPosterior.get(z))))) {
                            automatoValido = true
                            sair2 = true
                            break
                        }else if ((funcoesAtual.get(j).get(2) == funcoesPosterior.get(z).get(2)
                                    && funcoesPosterior.get(z).get(0) != funcoesPosterior.get(z).get(2)
                                    && funcoesAnterior.get(j) == funcoesPosterior.get(z))
                                    && (!transicoesDistantes(funcoesAtual.get(j), funcoesPosterior.get(z)))) {
                            automatoValido = true
                            sair2 = true
                            break
                        }else if ((funcoesAtual.get(j).get(2) == funcoesPosterior.get(z).get(0)
                                        && funcoesPosterior.get(z).get(0) != funcoesPosterior.get(z).get(2)
                                        && funcoesAnterior.get(j) == funcoesPosterior.get(z)
                                        && (!transicoesDistantes(funcoesAtual.get(j), funcoesPosterior.get(z))))) {
                            automatoValido = true
                            sair2 = true
                            break
                        }else {
                            automatoValido = false
                            sair2 = true
                            sair = true
                        }

                    }else if (funcoesAtual.get(j).get(0) == funcoesPosterior.get(z).get(0)
                            && (!transicoesDistantes(funcoesAtual.get(j), funcoesPosterior.get(z)))
                            && funcoesAtual.get(j).get(2) == funcoesAtual.get(j).get(0)) {
                        automatoValido = true
                        sair2 = true
                        break
                    }else {
                        automatoValido = false
                        sair2 = true
                        sair = true
                    }

                    z += 1
                }

                if (sair2) {
                    break
                }

                j += 1
            }


            if (i == (input.length-2)) {

                if (itemIsInFinalState(funcoesPosterior)) {
                    automatoValido = true
                }else {
                    automatoValido = false
                }

                break
            }

            if (sair) {
                break
            }

            i += 1
        }

        if (automatoValido) {
            etSaida.text = Editable.Factory.getInstance().newEditable("S")
        }else {
            etSaida.text = Editable.Factory.getInstance().newEditable("N")
        }

    }

    fun transicoesDistantes(fa: String, fp: String): Boolean {
        var i = 1
        var j = 1 // (não é o bairro)

        while(i <= this.funcoes.size) {
            if (fa == this.funcoes.get(i-1)) {
                break
            }
            i += 1
        }

        while(j <= this.funcoes.size) {
            if (fp == this.funcoes.get(j-1)) {
                break
            }

            j += 1
        }

        val dif = Math.abs(i - j)

        return dif > 1
    }

    fun itemIsInFinalState(funcoes: ArrayList<String>): Boolean {
        var i = 0

        var sair: Boolean = false
        while(i < funcoes.count()) {
            var j = 0

            while(j < estadosFinais.count()) {
                if (funcoes.get(i).get(2).toString() == estadosFinais.get(j).toString()) {
                    sair = true
                    break
                }

                j += 1
            }

            if (sair) {
                break
            }

            i += 1
        }

        if (sair) {
            return true
        }else {
            return false
        }
    }

    fun getFuncoesItem(item: String): ArrayList<String> {
        val lista = ArrayList<String>()
        var i = 0

        while(i < this.funcoes.count()) {
            if (this.funcoes.get(i).contains(item)) {
                lista.add(this.funcoes.get(i))
            }

            i += 1
        }

        return lista
    }

}
