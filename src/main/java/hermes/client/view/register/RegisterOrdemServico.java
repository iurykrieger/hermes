package hermes.client.view.register;

import com.hermes.components.HMFields.HMFormattedTextField;
import com.hermes.components.HMFrames.HMPanel;
import com.hermes.components.HMTables.HMTableModel;
import hermes.client.controll.mecanico.MecanicoController;
import hermes.client.controll.mecanico.MecanicoOrdemServicoController;
import hermes.client.controll.ordemDeServico.OrdemDeServicoController;
import hermes.client.controll.ordemDeServico.ParcelaOrdemServicoController;
import hermes.client.controll.ordemDeServico.ItemExternoController;
import hermes.client.controll.ordemDeServico.ItemOrdemServicoController;
import hermes.client.controll.produto.ProdutoController;
import hermes.client.controll.vendedor.VendedorController;
import hermes.client.dao.ClienteBean;
import hermes.client.dao.ProdutoBean;
import hermes.client.main.Main;
import hermes.client.view.search.SearchOrdemServico;
import hermes.client.view.utils.PrintRelatorio;
import hermes.client.view.select.SelectCliente;
import hermes.client.view.select.SelectEquipamento;
import hermes.client.view.select.SelectMecanico;
import hermes.client.view.select.SelectProduto;
import hermes.client.view.select.SelectProdutoExterno;
import hermes.ejb.entidades.ClienteInfo;
import hermes.ejb.entidades.Equipamento;
import hermes.ejb.entidades.Estoque;
import hermes.ejb.entidades.ItemExternoOrdemServico;
import hermes.ejb.entidades.ItemOrdemServico;
import hermes.ejb.entidades.MecanicoInfo;
import hermes.ejb.entidades.MecanicoOrdemServico;
import hermes.ejb.entidades.OrdemDeServico;
import hermes.ejb.entidades.ParcelaOdemDeServico;
import hermes.ejb.entidades.Produto;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author iury
 */
public class RegisterOrdemServico extends HMPanel {

    private OrdemDeServico os;
    private ClienteInfo cliente;
    private Equipamento equip;
    private List<MecanicoOrdemServico> mecanicos;
    private List<ItemOrdemServico> itens;
    private List<ItemExternoOrdemServico> itensExternos;
    private List<ParcelaOdemDeServico> parcelas;
    private double valor;
    private int index = -1;
    private SearchOrdemServico frame;

    public void initFields() {
        mecanicos = new ArrayList();
        itens = new ArrayList();
        itensExternos = new ArrayList();
        parcelas = new ArrayList();
        tf_cliente.setObrigatory(true);
        tf_equipamento.setObrigatory(true);
        tf_data_abertura.setObrigatory(true);
        tf_parcelas.setObrigatory(true);
        tf_data_abertura.setMask(HMFormattedTextField.DATA);
        tf_data_fechamento.setObrigatory(true);
        tf_data_fechamento.setMask(HMFormattedTextField.DATA);
        tf_cliente.setEditable(false);
        tf_equipamento.setEditable(false);
        tf_parcelas.setEditable(false);
        tf_parcelas.setEnabled(false);
        tf_valor.setEditable(false);
        tf_total.setEditable(false);
        tf_vencimento.setEditable(false);
        tf_vencimento.setObrigatory(true);
        tf_pago.setObrigatory(true);
        tf_pago.setEditable(false);
        tf_valor_item_externo.setEditable(false);
        tf_valor_item_externo.setEnabled(false);
        tf_valor_mao_obra.setEditable(false);
        tf_valor_mao_obra.setEnabled(false);
        tf_valor_produtos.setEditable(false);
        tf_valor_produtos.setEnabled(false);
        tf_pago.setValid(true);
        //TABLES
        String[] colunasItens = {"", "", "Quantidade", "Produto", ""};
        String[] ocultasItens = {"serialVersionUID", "idItemOrdemServico", "idOrdemServico"};
        tbl_itens.setModelo(new HMTableModel(null, ItemOrdemServico.class, colunasItens));
        tbl_itens.ocultarColunas(ocultasItens);
        String[] colunasMecanicos = {"", "", "Número de Horas", "", "Mecânico"};
        String[] ocultasMecanicos = {"serialVersionUID", "idMecanicoOrdemServico", "idOrdemServico"};
        tbl_mecanico.setModelo(new HMTableModel(null, MecanicoOrdemServico.class, colunasMecanicos));
        tbl_mecanico.ocultarColunas(ocultasMecanicos);
        String[] colunasItensExternos = {"", "", "Qtdade", "Nome", "", "Custo", "Valor Venda", "", "Fornecedor"};
        String[] ocultasItensExternos = {"serialVersionUID", "descricao", "idItemExternoOrdemServico", "idOrdemServico", "idOrdemServico"};
        tbl_item_externo.setModelo(new HMTableModel(null, ItemExternoOrdemServico.class, colunasItensExternos));
        tbl_item_externo.ocultarColunas(ocultasItensExternos);
    }

    public void calcularValor() {
        double value = tf_valor_item_externo.getValue().doubleValue()
                + tf_valor_mao_obra.getValue().doubleValue()
                + tf_valor_produtos.getValue().doubleValue();
        this.valor = value;
        tf_valor.setValue(BigDecimal.valueOf(value));
        calculaAcrescimoDesconto();
    }

    public void calculaAcrescimoDesconto() {
        double value = this.valor + (tf_acrescimo.getValue().doubleValue() - tf_desconto.getValue().doubleValue());
        if (value < 0) {
            tf_total.setValue(BigDecimal.valueOf(0));
        } else {
            tf_total.setValue(BigDecimal.valueOf(value));
        }
        double interest = value + (value * (tf_juros.getValue().doubleValue() / 100));
        tf_total.setValue(BigDecimal.valueOf(interest));
        if (!parcelas.isEmpty()) {
            double parcela = tf_total.getValue().doubleValue() / parcelas.size();
            for (ParcelaOdemDeServico p : parcelas) {
                p.setValorParcela(BigDecimal.valueOf(parcela));
            }
            tf_parcelas.setText(parcelas.size() + "x de R$ " + parcelas.get(0).getValorParcela());
            tf_parcelas.setValid(true);
        }
    }

    public void addParcelas(List<ParcelaOdemDeServico> parcelas) {
        this.parcelas = parcelas;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        tf_vencimento.setText(df.format(parcelas.get(parcelas.size() - 1).getDthParcela()));
        tf_parcelas.setText(parcelas.size() + "x de R$ " + parcelas.get(0).getValorParcela());
        tf_parcelas.setValid(true);
        tf_vencimento.setValid(true);
    }

    public void addProduto(ItemOrdemServico ios) {
        this.itens.add(ios);
        tbl_itens.getModelo().addObject(ios);
        tbl_itens.updateUI();
        Estoque e;
        try {
            e = new ProdutoBean().consultaEstoqueByProduto(ios.getIdProduto());
            double value = tf_valor_produtos.getValue().doubleValue() + (e.getValorVenda().doubleValue() * ios.getQuantidade().doubleValue());
            tf_valor_produtos.setValue(BigDecimal.valueOf(value));
            calcularValor();
        } catch (Exception ex) {
            Logger.getLogger(RegisterOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addProdutoExterno(ItemExternoOrdemServico ieos) {
        this.itensExternos.add(ieos);
        tbl_item_externo.getModelo().addObject(ieos);
        tbl_item_externo.updateUI();
        double value = tf_valor_item_externo.getValue().doubleValue() + (ieos.getValorVenda().doubleValue() * ieos.getQuantidade().doubleValue());
        tf_valor_item_externo.setValue(BigDecimal.valueOf(value));
        calcularValor();
    }

    public void addEquipamento(Equipamento equip) {
        this.equip = equip;
        tf_equipamento.setText(this.equip.getNome());
        tf_equipamento.setValid(true);
    }

    public void addMecanico(MecanicoOrdemServico mos) {
        this.mecanicos.add(mos);
        tbl_mecanico.getModelo().addObject(mos);
        tbl_mecanico.updateUI();
        MecanicoInfo mi = new MecanicoController().consultaMecanicoInfoByMecanico(mos.getIdMecanico());
        double value = tf_valor_mao_obra.getValue().doubleValue() + (mi.getValorHora().doubleValue() * mos.getNumeroHoras().doubleValue());
        tf_valor_mao_obra.setValue(BigDecimal.valueOf(value));
        calcularValor();
    }

    public void addCliente(ClienteInfo cliente) {
        this.cliente = cliente;
        if (this.cliente.getIdCliente().getTipoPessoa().equals("Física")) {
            tf_cliente.setText(this.cliente.getIdCliente().getNome() + " - " + this.cliente.getIdCliente().getCpf());
        } else {
            tf_cliente.setText(this.cliente.getIdCliente().getNome() + " - " + this.cliente.getIdCliente().getCnpj());
        }
        tf_cliente.setValid(true);
    }

    public void remSelectedProduto() {
        if (tbl_itens.getSelectedRow() != -1) {
            ItemOrdemServico ios = (ItemOrdemServico) tbl_itens.getSelectedItem();
            Estoque e;
            try {
                e = new ProdutoBean().consultaEstoqueByProduto(ios.getIdProduto());
                tbl_itens.removeSelectedItem();
                tbl_itens.updateUI();
                double value = tf_valor_produtos.getValue().doubleValue() - (e.getValorVenda().doubleValue() * ios.getQuantidade().doubleValue());
                tf_valor_produtos.setValue(BigDecimal.valueOf(value));
                itens.remove(ios);
                calcularValor();
            } catch (Exception ex) {
                Logger.getLogger(RegisterOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto a ser excluido!");
        }
    }

    public void remSelectedMecanico() {
        if (tbl_mecanico.getSelectedRow() != -1) {
            MecanicoOrdemServico mos = (MecanicoOrdemServico) tbl_mecanico.getSelectedItem();
            MecanicoInfo mi = new MecanicoController().consultaMecanicoInfoByMecanico(mos.getIdMecanico());
            tbl_mecanico.removeSelectedItem();
            tbl_mecanico.updateUI();
            mecanicos.remove(mos);
            double value = tf_valor_mao_obra.getValue().doubleValue() - (mi.getValorHora().doubleValue() * mos.getNumeroHoras().doubleValue());
            tf_valor_mao_obra.setValue(BigDecimal.valueOf(value));
            calcularValor();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um mecanico a ser excluido!");
        }
    }

    public void remSelectedProdutoExterno() {
        if (tbl_item_externo.getSelectedRow() != -1) {
            ItemExternoOrdemServico ieos = (ItemExternoOrdemServico) tbl_item_externo.getSelectedItem();
            tbl_item_externo.removeSelectedItem();
            itensExternos.remove(ieos);
            tbl_item_externo.updateUI();
            double value = tf_valor_item_externo.getValue().doubleValue() - (ieos.getValorVenda().doubleValue() * ieos.getQuantidade().doubleValue());
            tf_valor_item_externo.setValue(BigDecimal.valueOf(value));
            calcularValor();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto externo a ser excluido!");
        }
    }

    public void searchEquipamento() {
        if (!tf_cliente.getText().equals("")) {
            try {
                SelectEquipamento se = new SelectEquipamento(cliente, this);
                Main.desktop.addInternalFrame(se);
            } catch (Exception ex) {
                Logger.getLogger(RegisterOrdemServico.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente primeiro");
        }
    }

    public void searchCliente() {
        try {
            SelectCliente sc = new SelectCliente(this);
            Main.desktop.addInternalFrame(sc);
        } catch (Exception ex) {
            Logger.getLogger(RegisterOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchProduto() {
        try {
            SelectProduto sp = new SelectProduto(this, itens);
            Main.desktop.addInternalFrame(sp);
        } catch (Exception ex) {
            Logger.getLogger(RegisterOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchMecanico() {
        try {
            SelectMecanico sm = new SelectMecanico(this);
            Main.desktop.addInternalFrame(sm);
        } catch (Exception ex) {
            Logger.getLogger(RegisterOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchItemExterno() {
        try {
            SelectProdutoExterno spe = new SelectProdutoExterno(this);
            Main.desktop.addInternalFrame(spe);
        } catch (Exception ex) {
            Logger.getLogger(RegisterOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addParcelas() {
        try {
            RegisterParcela cp = new RegisterParcela(tf_total.getValue(), this);
            Main.desktop.addInternalFrame(cp);
        } catch (Exception ex) {
            Logger.getLogger(RegisterOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean verificaCampos() {
        if (tf_cliente.isValid() && tf_equipamento.isValid() && !tp_desc_servico.getText().isEmpty()
                && tf_data_abertura.isValid() && tf_parcelas.isValid() && tf_vencimento.isValid()) {
            return true;
        } else {
            return false;
        }
    }

    public void cadastrarOrdemDeServico() {
        if (verificaCampos()) {
            try {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                os.setAcrescimo(tf_acrescimo.getValue());
                os.setDesconto(tf_desconto.getValue());
                os.setDescricaoServico(tp_desc_servico.getText());
                os.setDthCriacao(df.parse(tf_data_abertura.getText()));
                os.setDthFechamento(df.parse(tf_data_fechamento.getText()));
                os.setDthVencimento(df.parse(tf_vencimento.getText()));
                os.setIdEquipamento(equip);
                os.setIdVendedor(new VendedorController().consultaVendedores().get(0));
                os.setJuros(tf_juros.getValue());
                os.setObservacao(tp_obs.getText());
                os.setPago(false);
                os.setProblema(tp_problema.getText());
                os.setValor(tf_valor.getValue());
                os.setValorTotal(tf_total.getValue());
                //CADASTRAR A OS
                this.os = new OrdemDeServicoController().cadastraOrdemDeServico(os);
                //CADASTRAR OS DEPENDENTES DA OS
                MecanicoOrdemServicoController mecanicoController = new MecanicoOrdemServicoController();
                for (MecanicoOrdemServico mecanico : mecanicos) {
                    mecanico.setIdOrdemServico(this.os);
                    mecanicoController.cadastraMecanicoOrdemServico(mecanico);
                }
                ItemOrdemServicoController itemController = new ItemOrdemServicoController();
                for (ItemOrdemServico item : itens) {
                    item.setIdOrdemServico(this.os);
                    item = itemController.cadastraItemOrdemServico(item);
                    Estoque e = new ProdutoBean().consultaEstoqueByProduto(item.getIdProduto());
                    long qtdade = e.getQuantidade() - item.getQuantidade().longValue();
                    e.setQuantidade(qtdade);
                    new ProdutoController().registerEstoque(item.getIdProduto(), e);
                }
                ItemExternoController itemExternoController = new ItemExternoController();
                int i = 0;
                for (ItemExternoOrdemServico itemExterno : itensExternos) {
                    itemExterno.setIdOrdemServico(this.os);
                    itemExterno = itemExternoController.cadastraItemExterno(itemExterno);
                    itensExternos.set(i, itemExterno);
                    i++;
                }
                ParcelaOrdemServicoController parcelaController = new ParcelaOrdemServicoController();
                i = 0;
                parcelaController.removeAllParcelasByOrdemDeServico(this.os);
                for (ParcelaOdemDeServico parcela : parcelas) {
                    parcela.setIdOrdemServico(this.os);
                    parcela.setIdParcelaOrdemServico(null);
                    parcela = parcelaController.cadastraOrdemDeServico(parcela);
                    parcelas.set(i, parcela);
                    i++;
                }
                this.os.setItemExternoOrdemServicoList(itensExternos);
                this.os.setItemOrdemServicoList(itens);
                this.os.setMecanicoOrdemServicoList(mecanicos);
                this.os.setParcelaOdemDeServicoList(parcelas);
                for (ItemOrdemServico ios : itens) {
                    Produto p = ios.getIdProduto();
                    List<Estoque> es = new ArrayList();
                    es.add(new ProdutoBean().consultaEstoqueByProduto(p));
                    p.setEstoqueList(es);
                }
                JOptionPane.showMessageDialog(this, "Ordem de Serviço cadastrada com sucesso!");
                PrintRelatorio pr = new PrintRelatorio(this.os);
                Main.desktop.addInternalFrame(pr);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Não foi possível cadastrar a Ordem de Serviço");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!");
        }
    }

    public void buildOrdemDeServico() {
        try {
            addCliente(new ClienteBean().getClienteInfoByCliente(this.os.getIdEquipamento().getIdCliente()));
        } catch (Exception ex) {
            Logger.getLogger(RegisterOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
        }
        addEquipamento(this.os.getIdEquipamento());
        addParcelas(new ParcelaOrdemServicoController().consultaParcelasByOrdemDeServico(this.os));
        List<ItemOrdemServico> itens = new ItemOrdemServicoController().consultaItensOrdemServicoByOrdemServico(this.os);
        for (ItemOrdemServico i : itens) {
            addProduto(i);
        }
        List<ItemExternoOrdemServico> itensExternos = new ItemExternoController().consultaItensExternosByOrdemServico(this.os);
        for (ItemExternoOrdemServico i : itensExternos) {
            addProdutoExterno(i);
        }
        List<MecanicoOrdemServico> mecanicos = new MecanicoOrdemServicoController().consultaMecanicosOrdemServicoByOrdemServico(this.os);
        for (MecanicoOrdemServico m : mecanicos) {
            addMecanico(m);
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        tp_desc_servico.setText(this.os.getDescricaoServico());
        tf_data_abertura.setText(df.format(this.os.getDthCriacao()));
        try {
            tf_data_fechamento.setText(df.format(this.os.getDthFechamento()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tf_vencimento.setText(df.format(this.os.getDthVencimento()));
        tp_obs.setText(this.os.getObservacao());
        tp_problema.setText(this.os.getProblema());
        tf_acrescimo.setValue(this.os.getAcrescimo());
        tf_desconto.setValue(this.os.getDesconto());
        tf_juros.setValue(this.os.getJuros());

        tf_data_abertura.setValid(true);
        tf_data_fechamento.setValid(true);
        tf_vencimento.setValid(true);
        calculaAcrescimoDesconto();
    }

    public RegisterOrdemServico() {
        initComponents();
        initFields();
        setSize(1189, 590);
        this.os = new OrdemDeServico();
    }

    public RegisterOrdemServico(SearchOrdemServico frame, OrdemDeServico os, int index) {
        initComponents();
        initFields();
        setSize(1189, 590);
        this.os = os;
        this.frame = frame;
        this.index = index;
        buildOrdemDeServico();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_cad_ordem_servico = new javax.swing.JPanel();
        pn_servico = new javax.swing.JPanel();
        lb_cliente = new javax.swing.JLabel();
        tf_cliente = new com.hermes.components.HMFields.HMTextField();
        lb_equipamento = new javax.swing.JLabel();
        tf_equipamento = new com.hermes.components.HMFields.HMTextField();
        btn_search_cliente = new javax.swing.JButton();
        btn_search_equip = new javax.swing.JButton();
        lb_desc_servico = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tp_desc_servico = new javax.swing.JTextPane();
        lb_data_abertura = new javax.swing.JLabel();
        tf_data_abertura = new com.hermes.components.HMFields.HMFormattedTextField();
        lb_data_fechamento = new javax.swing.JLabel();
        tf_data_fechamento = new com.hermes.components.HMFields.HMFormattedTextField();
        lb_vendedor_nome = new javax.swing.JLabel();
        pn_itens = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_itens = new com.hermes.components.HMTables.HMTable();
        btn_adc_itens = new javax.swing.JButton();
        btn_rem_itens = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tf_valor_produtos = new com.hermes.components.HMFields.HMMonetaryField();
        pn_mao_obra = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_mecanico = new com.hermes.components.HMTables.HMTable();
        btn_adc_mecanico = new javax.swing.JButton();
        btn_rem_mecanico = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tf_valor_mao_obra = new com.hermes.components.HMFields.HMMonetaryField();
        pn_pagamento = new javax.swing.JPanel();
        lb_acrescimo = new javax.swing.JLabel();
        lb_desconto = new javax.swing.JLabel();
        lb_juros = new javax.swing.JLabel();
        tf_acrescimo = new com.hermes.components.HMFields.HMMonetaryField();
        tf_desconto = new com.hermes.components.HMFields.HMMonetaryField();
        lb_parcelas = new javax.swing.JLabel();
        tf_parcelas = new com.hermes.components.HMFields.HMTextField();
        btn_search_parcelas = new javax.swing.JButton();
        lb_valor = new javax.swing.JLabel();
        tf_valor = new com.hermes.components.HMFields.HMMonetaryField();
        lb_total = new javax.swing.JLabel();
        tf_total = new com.hermes.components.HMFields.HMMonetaryField();
        lb_vencimento = new javax.swing.JLabel();
        tf_vencimento = new com.hermes.components.HMFields.HMFormattedTextField();
        lb_pago = new javax.swing.JLabel();
        tf_pago = new com.hermes.components.HMFields.HMTextField();
        tf_juros = new com.hermes.components.HMFields.HMInterestField();
        pb_itens_externos = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_item_externo = new com.hermes.components.HMTables.HMTable();
        btn_adc_item_externo = new javax.swing.JButton();
        btn_rem_item_externo = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tf_valor_item_externo = new com.hermes.components.HMFields.HMMonetaryField();
        lb_obs = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tp_obs = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tp_problema = new javax.swing.JTextPane();

        setOpaque(false);

        pn_cad_ordem_servico.setOpaque(false);

        pn_servico.setBorder(javax.swing.BorderFactory.createTitledBorder("Serviço"));
        pn_servico.setOpaque(false);

        lb_cliente.setText("Cliente:");

        tf_cliente.setEnabled(false);
        tf_cliente.setObrigatory(true);

        lb_equipamento.setText("Equipamento:");

        tf_equipamento.setEnabled(false);
        tf_equipamento.setObrigatory(true);

        btn_search_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search_clienteActionPerformed(evt);
            }
        });

        btn_search_equip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search_equipActionPerformed(evt);
            }
        });

        lb_desc_servico.setText("Desc. Serviço:");

        jScrollPane1.setViewportView(tp_desc_servico);

        lb_data_abertura.setText("Data Abertura:");

        tf_data_abertura.setMask(HMFormattedTextField.DATA);
        tf_data_abertura.setObrigatory(true);

        lb_data_fechamento.setText("Data Fechamento:");

        tf_data_fechamento.setMask(HMFormattedTextField.DATA);

        javax.swing.GroupLayout pn_servicoLayout = new javax.swing.GroupLayout(pn_servico);
        pn_servico.setLayout(pn_servicoLayout);
        pn_servicoLayout.setHorizontalGroup(
            pn_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_servicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pn_servicoLayout.createSequentialGroup()
                        .addComponent(lb_cliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(btn_search_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_servicoLayout.createSequentialGroup()
                        .addComponent(lb_desc_servico)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_data_fechamento)
                    .addComponent(lb_data_abertura, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_equipamento, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_data_abertura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pn_servicoLayout.createSequentialGroup()
                        .addComponent(tf_equipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_search_equip, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(tf_data_fechamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pn_servicoLayout.setVerticalGroup(
            pn_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_servicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_search_equip, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_equipamento)
                        .addComponent(tf_equipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tf_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_cliente))
                    .addComponent(btn_search_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pn_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_servicoLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lb_desc_servico)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_servicoLayout.createSequentialGroup()
                        .addGroup(pn_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_servicoLayout.createSequentialGroup()
                                .addGroup(pn_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lb_data_abertura)
                                    .addComponent(tf_data_abertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pn_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lb_data_fechamento)
                                    .addComponent(tf_data_fechamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(41, 41, 41))))
        );

        lb_vendedor_nome.setText(" ");

        pn_itens.setBorder(javax.swing.BorderFactory.createTitledBorder("Itens Utilizados"));
        pn_itens.setOpaque(false);

        tbl_itens.setForeground(new java.awt.Color(0, 0, 0));
        tbl_itens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tbl_itens);

        btn_adc_itens.setText("Adicionar");
        btn_adc_itens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_adc_itensActionPerformed(evt);
            }
        });

        btn_rem_itens.setText("Remover");
        btn_rem_itens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rem_itensActionPerformed(evt);
            }
        });

        jLabel1.setText("Valor Total:");

        javax.swing.GroupLayout pn_itensLayout = new javax.swing.GroupLayout(pn_itens);
        pn_itens.setLayout(pn_itensLayout);
        pn_itensLayout.setHorizontalGroup(
            pn_itensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_itensLayout.createSequentialGroup()
                .addGroup(pn_itensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_itensLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(btn_adc_itens)
                        .addGap(18, 18, 18)
                        .addComponent(btn_rem_itens)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_valor_produtos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_itensLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_itensLayout.setVerticalGroup(
            pn_itensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_itensLayout.createSequentialGroup()
                .addGroup(pn_itensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_adc_itens)
                    .addComponent(btn_rem_itens)
                    .addComponent(jLabel1)
                    .addComponent(tf_valor_produtos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );

        pn_mao_obra.setBorder(javax.swing.BorderFactory.createTitledBorder("Mão de Obra"));
        pn_mao_obra.setOpaque(false);

        tbl_mecanico.setForeground(new java.awt.Color(0, 0, 0));
        tbl_mecanico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tbl_mecanico);

        btn_adc_mecanico.setText("Adicionar");
        btn_adc_mecanico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_adc_mecanicoActionPerformed(evt);
            }
        });

        btn_rem_mecanico.setText("Remover");
        btn_rem_mecanico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rem_mecanicoActionPerformed(evt);
            }
        });

        jLabel2.setText("Valor Total:");

        javax.swing.GroupLayout pn_mao_obraLayout = new javax.swing.GroupLayout(pn_mao_obra);
        pn_mao_obra.setLayout(pn_mao_obraLayout);
        pn_mao_obraLayout.setHorizontalGroup(
            pn_mao_obraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_mao_obraLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(btn_adc_mecanico)
                .addGap(18, 18, 18)
                .addComponent(btn_rem_mecanico)
                .addGap(89, 89, 89)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_valor_mao_obra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_mao_obraLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        pn_mao_obraLayout.setVerticalGroup(
            pn_mao_obraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_mao_obraLayout.createSequentialGroup()
                .addGroup(pn_mao_obraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_adc_mecanico)
                    .addComponent(btn_rem_mecanico)
                    .addComponent(jLabel2)
                    .addComponent(tf_valor_mao_obra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pn_pagamento.setBorder(javax.swing.BorderFactory.createTitledBorder("Pagamento"));
        pn_pagamento.setOpaque(false);

        lb_acrescimo.setText("Acréscimo:");

        lb_desconto.setText("Desconto:");

        lb_juros.setText("Juros:");

        tf_acrescimo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_acrescimoKeyReleased(evt);
            }
        });

        tf_desconto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_descontoKeyReleased(evt);
            }
        });

        lb_parcelas.setText("Parcelas:");

        tf_parcelas.setEditable(false);
        tf_parcelas.setObrigatory(true);

        btn_search_parcelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search_parcelasActionPerformed(evt);
            }
        });

        lb_valor.setText("Valor:");

        tf_valor.setEditable(false);

        lb_total.setText("Total:");

        tf_total.setEditable(false);

        lb_vencimento.setText("Venc.:");

        tf_vencimento.setEditable(false);
        tf_vencimento.setMask(HMFormattedTextField.DATA);
        tf_vencimento.setObrigatory(true);

        lb_pago.setText("Pago:");

        tf_pago.setEditable(false);

        tf_juros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_jurosKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pn_pagamentoLayout = new javax.swing.GroupLayout(pn_pagamento);
        pn_pagamento.setLayout(pn_pagamentoLayout);
        pn_pagamentoLayout.setHorizontalGroup(
            pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_pagamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pn_pagamentoLayout.createSequentialGroup()
                        .addComponent(lb_acrescimo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_acrescimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lb_parcelas))
                    .addGroup(pn_pagamentoLayout.createSequentialGroup()
                        .addGroup(pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lb_juros)
                            .addComponent(lb_desconto))
                        .addGap(7, 7, 7)
                        .addGroup(pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tf_juros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tf_desconto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_valor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lb_vencimento, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(4, 4, 4)
                .addGroup(pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_pagamentoLayout.createSequentialGroup()
                        .addComponent(tf_parcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_search_parcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pn_pagamentoLayout.createSequentialGroup()
                        .addGroup(pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pn_pagamentoLayout.createSequentialGroup()
                                .addComponent(tf_vencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lb_pago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pn_pagamentoLayout.createSequentialGroup()
                                .addComponent(tf_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lb_total)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_pagamentoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(tf_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tf_total, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                        .addGap(44, 44, 44))))
        );
        pn_pagamentoLayout.setVerticalGroup(
            pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_pagamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_search_parcelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_acrescimo)
                        .addComponent(tf_acrescimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_parcelas)
                        .addComponent(tf_parcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_desconto)
                    .addComponent(tf_desconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_valor)
                    .addComponent(tf_valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_total))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_vencimento)
                        .addComponent(tf_vencimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lb_pago)
                        .addComponent(tf_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_pagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_juros)
                        .addComponent(tf_juros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );

        pb_itens_externos.setBorder(javax.swing.BorderFactory.createTitledBorder("Itens Externos"));
        pb_itens_externos.setOpaque(false);

        tbl_item_externo.setForeground(new java.awt.Color(0, 0, 0));
        tbl_item_externo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tbl_item_externo);

        btn_adc_item_externo.setText("Adicionar");
        btn_adc_item_externo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_adc_item_externoActionPerformed(evt);
            }
        });

        btn_rem_item_externo.setText("Remover");
        btn_rem_item_externo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rem_item_externoActionPerformed(evt);
            }
        });

        jLabel3.setText("Valor Total:");

        javax.swing.GroupLayout pb_itens_externosLayout = new javax.swing.GroupLayout(pb_itens_externos);
        pb_itens_externos.setLayout(pb_itens_externosLayout);
        pb_itens_externosLayout.setHorizontalGroup(
            pb_itens_externosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pb_itens_externosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pb_itens_externosLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(btn_adc_item_externo)
                .addGap(18, 18, 18)
                .addComponent(btn_rem_item_externo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_valor_item_externo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );
        pb_itens_externosLayout.setVerticalGroup(
            pb_itens_externosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pb_itens_externosLayout.createSequentialGroup()
                .addGroup(pb_itens_externosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_adc_item_externo)
                    .addComponent(btn_rem_item_externo)
                    .addComponent(jLabel3)
                    .addComponent(tf_valor_item_externo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addContainerGap())
        );

        lb_obs.setBorder(javax.swing.BorderFactory.createTitledBorder("Observações"));
        lb_obs.setOpaque(false);

        jScrollPane5.setViewportView(tp_obs);

        javax.swing.GroupLayout lb_obsLayout = new javax.swing.GroupLayout(lb_obs);
        lb_obs.setLayout(lb_obsLayout);
        lb_obsLayout.setHorizontalGroup(
            lb_obsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lb_obsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        lb_obsLayout.setVerticalGroup(
            lb_obsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lb_obsLayout.createSequentialGroup()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Problema Encontrado"));
        jPanel1.setOpaque(false);

        jScrollPane6.setViewportView(tp_problema);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );

        javax.swing.GroupLayout pn_cad_ordem_servicoLayout = new javax.swing.GroupLayout(pn_cad_ordem_servico);
        pn_cad_ordem_servico.setLayout(pn_cad_ordem_servicoLayout);
        pn_cad_ordem_servicoLayout.setHorizontalGroup(
            pn_cad_ordem_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_cad_ordem_servicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_cad_ordem_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_cad_ordem_servicoLayout.createSequentialGroup()
                        .addComponent(lb_obs, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_cad_ordem_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pn_itens, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pn_servico, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_cad_ordem_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_cad_ordem_servicoLayout.createSequentialGroup()
                        .addComponent(pn_pagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(lb_vendedor_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pn_mao_obra, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pb_itens_externos, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_cad_ordem_servicoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(229, 229, 229))
        );
        pn_cad_ordem_servicoLayout.setVerticalGroup(
            pn_cad_ordem_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_cad_ordem_servicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_cad_ordem_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_cad_ordem_servicoLayout.createSequentialGroup()
                        .addGroup(pn_cad_ordem_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pn_pagamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pn_servico, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_cad_ordem_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pn_cad_ordem_servicoLayout.createSequentialGroup()
                                .addComponent(pn_mao_obra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pb_itens_externos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pn_cad_ordem_servicoLayout.createSequentialGroup()
                                .addComponent(pn_itens, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pn_cad_ordem_servicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_obs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addComponent(lb_vendedor_nome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_cad_ordem_servico, javax.swing.GroupLayout.PREFERRED_SIZE, 1189, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_cad_ordem_servico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_search_parcelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search_parcelasActionPerformed
        addParcelas();
    }//GEN-LAST:event_btn_search_parcelasActionPerformed

    private void btn_search_equipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search_equipActionPerformed
        searchEquipamento();
    }//GEN-LAST:event_btn_search_equipActionPerformed

    private void btn_search_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search_clienteActionPerformed
        searchCliente();
    }//GEN-LAST:event_btn_search_clienteActionPerformed

    private void btn_adc_itensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_adc_itensActionPerformed
        searchProduto();
    }//GEN-LAST:event_btn_adc_itensActionPerformed

    private void btn_adc_mecanicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_adc_mecanicoActionPerformed
        searchMecanico();
    }//GEN-LAST:event_btn_adc_mecanicoActionPerformed

    private void tf_acrescimoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_acrescimoKeyReleased
        calculaAcrescimoDesconto();
    }//GEN-LAST:event_tf_acrescimoKeyReleased

    private void tf_descontoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_descontoKeyReleased
        calculaAcrescimoDesconto();
    }//GEN-LAST:event_tf_descontoKeyReleased

    private void btn_adc_item_externoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_adc_item_externoActionPerformed
        searchItemExterno();
    }//GEN-LAST:event_btn_adc_item_externoActionPerformed

    private void tf_jurosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_jurosKeyReleased
        calculaAcrescimoDesconto();
    }//GEN-LAST:event_tf_jurosKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cadastrarOrdemDeServico();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_rem_itensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rem_itensActionPerformed
        remSelectedProduto();
    }//GEN-LAST:event_btn_rem_itensActionPerformed

    private void btn_rem_mecanicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rem_mecanicoActionPerformed
        remSelectedMecanico();
    }//GEN-LAST:event_btn_rem_mecanicoActionPerformed

    private void btn_rem_item_externoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rem_item_externoActionPerformed
        remSelectedProdutoExterno();
    }//GEN-LAST:event_btn_rem_item_externoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_adc_item_externo;
    private javax.swing.JButton btn_adc_itens;
    private javax.swing.JButton btn_adc_mecanico;
    private javax.swing.JButton btn_rem_item_externo;
    private javax.swing.JButton btn_rem_itens;
    private javax.swing.JButton btn_rem_mecanico;
    private javax.swing.JButton btn_search_cliente;
    private javax.swing.JButton btn_search_equip;
    private javax.swing.JButton btn_search_parcelas;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lb_acrescimo;
    private javax.swing.JLabel lb_cliente;
    private javax.swing.JLabel lb_data_abertura;
    private javax.swing.JLabel lb_data_fechamento;
    private javax.swing.JLabel lb_desc_servico;
    private javax.swing.JLabel lb_desconto;
    private javax.swing.JLabel lb_equipamento;
    private javax.swing.JLabel lb_juros;
    private javax.swing.JPanel lb_obs;
    private javax.swing.JLabel lb_pago;
    private javax.swing.JLabel lb_parcelas;
    private javax.swing.JLabel lb_total;
    private javax.swing.JLabel lb_valor;
    private javax.swing.JLabel lb_vencimento;
    private javax.swing.JLabel lb_vendedor_nome;
    private javax.swing.JPanel pb_itens_externos;
    private javax.swing.JPanel pn_cad_ordem_servico;
    private javax.swing.JPanel pn_itens;
    private javax.swing.JPanel pn_mao_obra;
    private javax.swing.JPanel pn_pagamento;
    private javax.swing.JPanel pn_servico;
    private com.hermes.components.HMTables.HMTable tbl_item_externo;
    private com.hermes.components.HMTables.HMTable tbl_itens;
    private com.hermes.components.HMTables.HMTable tbl_mecanico;
    private com.hermes.components.HMFields.HMMonetaryField tf_acrescimo;
    private com.hermes.components.HMFields.HMTextField tf_cliente;
    private com.hermes.components.HMFields.HMFormattedTextField tf_data_abertura;
    private com.hermes.components.HMFields.HMFormattedTextField tf_data_fechamento;
    private com.hermes.components.HMFields.HMMonetaryField tf_desconto;
    private com.hermes.components.HMFields.HMTextField tf_equipamento;
    private com.hermes.components.HMFields.HMInterestField tf_juros;
    private com.hermes.components.HMFields.HMTextField tf_pago;
    private com.hermes.components.HMFields.HMTextField tf_parcelas;
    private com.hermes.components.HMFields.HMMonetaryField tf_total;
    private com.hermes.components.HMFields.HMMonetaryField tf_valor;
    private com.hermes.components.HMFields.HMMonetaryField tf_valor_item_externo;
    private com.hermes.components.HMFields.HMMonetaryField tf_valor_mao_obra;
    private com.hermes.components.HMFields.HMMonetaryField tf_valor_produtos;
    private com.hermes.components.HMFields.HMFormattedTextField tf_vencimento;
    private javax.swing.JTextPane tp_desc_servico;
    private javax.swing.JTextPane tp_obs;
    private javax.swing.JTextPane tp_problema;
    // End of variables declaration//GEN-END:variables
}
