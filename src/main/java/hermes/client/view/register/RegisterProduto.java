package hermes.client.view.register;

import com.hermes.components.HMFrames.HMPanel;
import com.hermes.components.HMTables.HMTableModel;
import com.hermes.components.utils.ImageFactory;
import hermes.client.controll.produto.ProdutoController;
import hermes.client.controll.tipoProduto.TipoProdutoController;
import hermes.client.controll.unidade.UnidadeController;
import hermes.client.listener.produto.Dispatcher;
import hermes.client.listener.produto.PanelListenerInterface;
import hermes.client.listener.produto.PanelListener;
import hermes.client.main.Main;
import hermes.client.view.utils.ImageUtils;
import hermes.ejb.entidades.Aplicacao;
import hermes.ejb.entidades.Descricao;
import hermes.ejb.entidades.Estoque;
import hermes.ejb.entidades.Produto;
import hermes.ejb.entidades.TipoProduto;
import hermes.ejb.entidades.Unidade;
import java.awt.HeadlessException;
import java.math.BigDecimal;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author iury
 */
public class RegisterProduto extends HMPanel {

    private ProdutoController controller;
    private String imagePath;

    public RegisterProduto() {
        this.controller = new ProdutoController();
        initComponents();
        initFields();
        setSize(636, 505);
    }

    @Override
    public void salvar() {
        try {
            //if (verificaCampos(this)) {
            String codigo = tf_codigo.getText();
            String nome = tf_nome.getText();
            String localizacao = tf_localizacao.getText();
            TipoProduto tipoProduto = new TipoProdutoController().getTipos().get(cb_tipo.getSelectedIndex());
            BigDecimal medidaInterna = tf_med_interna.getValue();
            BigDecimal medidaExterna = tf_med_externa.getValue();
            Unidade unidade = new UnidadeController().getUnidades().get(cb_unidade.getSelectedIndex());
            Produto produto = controller.buildProduto(codigo, imagePath, tipoProduto, unidade, localizacao, medidaExterna, medidaInterna, nome);
            produto = controller.registerProduto(produto);
            if (produto != null) {
                controller.setEstoquesInative(produto);
                long quantidade = Long.valueOf(spn_qtdade.getValue().toString());
                BigDecimal custo = tf_custo.getValue();
                BigDecimal valorVenda = tf_valor.getValue();
                Estoque e = controller.buildEstoque(custo, produto, quantidade, valorVenda);
                controller.registerEstoque(produto, e);
                controller.registerDescricoes(produto, tbl_desc.getLista());
                controller.registerAplicacoes(produto, tbl_aplic.getLista());
                JOptionPane.showMessageDialog(this, "Produto salvo com sucesso!");
                resetFrame();
            }
            /*} else {
             JOptionPane.showMessageDialog(this, "Todos os campos obrigatórios devem ser preenchidos!");
             }*/
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Números inválidos");
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Não foi possível salvar o produto!");
        }
    }

    @Override
    public void getReceivedObject(Object object) {
        if (object instanceof Descricao) {
            addDescricao((Descricao) object);
        } else if (object instanceof Aplicacao) {
            addAplicacao((Aplicacao) object);
        }
    }

    public void initFields() {
        //IMAGENS
        btn_escolher.setIcon(new ImageFactory().getIcon("open_folder_add.png", 25, 25));
        btn_baixar_foto.setIcon(new ImageFactory().getIcon("download_diskette.png", 25, 25));
        //FIELDS
        tf_codigo.setObrigatory(true);
        tf_codigo.setSpecial(true);
        tf_localizacao.setObrigatory(true);
        tf_nome.setObrigatory(true);
        spn_qtdade.setModel(new SpinnerNumberModel(0, 0, 999, 1));
        //COMBO BOXES
        controller.loadTipoProduto(cb_tipo);
        controller.loadUnidade(cb_unidade);
        //TABLES
        String colunas_desc[] = {"", "", "Descrição", ""};
        String colunas_aplic[] = {"", "", "Aplicação", ""};
        String colunas_ocultas_aplic[] = {"serialVersionUID", "idAplicacao", "idProduto"};
        String colunas_ocultas_desc[] = {"serialVersionUID", "idDescricao", "idProduto"};
        tbl_aplic.setModelo(new HMTableModel(null, Aplicacao.class, colunas_aplic));
        tbl_desc.setModelo(new HMTableModel(null, Descricao.class, colunas_desc));
        tbl_aplic.ocultarColunas(colunas_ocultas_aplic);
        tbl_desc.ocultarColunas(colunas_ocultas_desc);
        //BUTTONS
        btn_add_aplic.setIcon(new ImageFactory().getIcon("add.png", 35, 35));
        btn_add_desc.setIcon(new ImageFactory().getIcon("add.png", 35, 35));
        btn_rem_aplic.setIcon(new ImageFactory().getIcon("close.png", 35, 35));
        btn_rem_desc.setIcon(new ImageFactory().getIcon("close.png", 35, 35));
        //LISTENERS
        PanelListenerInterface listener = new PanelListener(this);
        Dispatcher.getInstance().addListener(listener);
    }

    public void getImage() {
        imagePath = controller.getImage(fileChooser, this);
        ImageIcon i = ImageUtils.getImageIconByFile(imagePath, lb_foto.getWidth(), lb_foto.getHeight());
        lb_foto.setIcon(i);
    }

    public void saveImage() {
        controller.saveImage(imagePath, fileChooser, this);
    }

    public void resetFrame() {
        resetaPanel(this);
        tbl_desc.getModelo().esvaziaTable();
        tbl_aplic.getModelo().esvaziaTable();
        tbl_aplic.updateUI();
        tbl_desc.updateUI();
        lb_foto.setIcon(null);
    }

    public void addDescricao(Descricao desc) {
        tbl_desc.addObject(desc);
        tbl_desc.updateUI();
    }

    public void alteraDescricao(Descricao desc, int rowIndex) {
        tbl_desc.alterObject(desc, rowIndex);
        tbl_desc.updateUI();
    }

    public void remSelectedDescricao() {
        if (tbl_desc.getSelectedRow() >= 0) {
            tbl_desc.removeSelectedItem();
            tbl_desc.updateUI();
        } else {
            JOptionPane.showMessageDialog(this, "Você precisa selecionar uma descrição para poder removê-la");
        }
    }

    public void addAplicacao(Aplicacao aplic) {
        tbl_aplic.addObject(aplic);
        tbl_aplic.updateUI();
    }

    public void alteraAplicacao(Aplicacao aplic, int rowIndex) {
        tbl_aplic.alterObject(aplic, rowIndex);
        tbl_aplic.updateUI();
    }

    public void remSelectedAplicacao() {
        if (tbl_aplic.getSelectedRow() >= 0) {
            tbl_aplic.removeSelectedItem();
            tbl_aplic.updateUI();
        } else {
            JOptionPane.showMessageDialog(this, "Você precisa selecionar uma aplicação para poder removê-la");
        }
    }

    public void newDescricao() {
        try {
            RegisterDescricao frame = new RegisterDescricao();
            Main.desktop.addInternalFrame(frame);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Não foi possível abrir uma nova descrição!");
            e.printStackTrace();
        }
    }

    public void newAplicacao() {
        try {
            RegisterAplicacao frame = new RegisterAplicacao();
            Main.desktop.addInternalFrame(frame);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Não foi possível abrir uma nova aplicação!");
            e.printStackTrace();
        }
    }

    public void alterarDescricao() {

    }

    public void alterarAplicacao() {
        
    }

    public void unitType() {
        if (cb_unidade.getSelectedItem().toString().equals("Litro") || cb_unidade.getSelectedItem().toString().equals("Metro")) {
            spn_qtdade.setModel(new SpinnerNumberModel(0, 0, 999, 0.1));
        } else {
            spn_qtdade.setModel(new SpinnerNumberModel(0, 0, 999, 1));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        pn_cadastro_produto = new javax.swing.JPanel();
        pn_medidas = new javax.swing.JPanel();
        lb_medida_interna = new javax.swing.JLabel();
        lb_medida_externa = new javax.swing.JLabel();
        lb_unidade = new javax.swing.JLabel();
        cb_unidade = new com.hermes.components.HMSelections.HMComboBox();
        tf_med_interna = new com.hermes.components.HMFields.HMDecimalField();
        tf_med_externa = new com.hermes.components.HMFields.HMDecimalField();
        tf_altura = new com.hermes.components.HMFields.HMDecimalField();
        lb_medida_interna1 = new javax.swing.JLabel();
        pn_descricao = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_desc = new com.hermes.components.HMTables.HMTable();
        btn_add_desc = new javax.swing.JButton();
        btn_rem_desc = new javax.swing.JButton();
        pn_aplicacoes = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_aplic = new com.hermes.components.HMTables.HMTable();
        btn_add_aplic = new javax.swing.JButton();
        btn_rem_aplic = new javax.swing.JButton();
        pn_identificacao = new javax.swing.JPanel();
        lb_codigo = new javax.swing.JLabel();
        tf_codigo = new com.hermes.components.HMFields.HMTextField();
        lb_nome = new javax.swing.JLabel();
        tf_nome = new com.hermes.components.HMFields.HMTextField();
        lb_localizacao = new javax.swing.JLabel();
        lb_tipo_produto = new javax.swing.JLabel();
        tf_localizacao = new com.hermes.components.HMFields.HMTextField();
        cb_tipo = new com.hermes.components.HMSelections.HMComboBox();
        tf_nome1 = new com.hermes.components.HMFields.HMTextField();
        jLabel1 = new javax.swing.JLabel();
        pn_valores = new javax.swing.JPanel();
        lb_custo = new javax.swing.JLabel();
        lb_valor_venda = new javax.swing.JLabel();
        tf_custo = new com.hermes.components.HMFields.HMMonetaryField();
        tf_valor = new com.hermes.components.HMFields.HMMonetaryField();
        lb_qtdade = new javax.swing.JLabel();
        spn_qtdade = new javax.swing.JSpinner();
        pn_foto = new javax.swing.JPanel();
        btn_escolher = new javax.swing.JButton();
        lb_foto = new javax.swing.JLabel();
        btn_baixar_foto = new javax.swing.JButton();

        setOpaque(false);

        pn_cadastro_produto.setOpaque(false);
        pn_cadastro_produto.setPreferredSize(new java.awt.Dimension(725, 533));

        pn_medidas.setBorder(javax.swing.BorderFactory.createTitledBorder("Medidas"));
        pn_medidas.setOpaque(false);

        lb_medida_interna.setText("Med. Interna:");

        lb_medida_externa.setText("Med. Externa:");

        lb_unidade.setText("Unidade:");

        cb_unidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_unidadeActionPerformed(evt);
            }
        });

        tf_altura.setEditable(false);

        lb_medida_interna1.setText("Altura:");

        javax.swing.GroupLayout pn_medidasLayout = new javax.swing.GroupLayout(pn_medidas);
        pn_medidas.setLayout(pn_medidasLayout);
        pn_medidasLayout.setHorizontalGroup(
            pn_medidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_medidasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_medidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_medida_interna)
                    .addComponent(lb_medida_interna1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_medidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pn_medidasLayout.createSequentialGroup()
                        .addComponent(tf_med_interna, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lb_medida_externa))
                    .addGroup(pn_medidasLayout.createSequentialGroup()
                        .addComponent(tf_altura, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lb_unidade)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_medidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cb_unidade, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                    .addComponent(tf_med_externa, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_medidasLayout.setVerticalGroup(
            pn_medidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_medidasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_medidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_medida_interna)
                    .addComponent(lb_medida_externa)
                    .addComponent(tf_med_interna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_med_externa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_medidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_unidade)
                    .addComponent(cb_unidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_altura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_medida_interna1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pn_descricao.setBorder(javax.swing.BorderFactory.createTitledBorder("Descrições"));
        pn_descricao.setOpaque(false);

        tbl_desc.setForeground(new java.awt.Color(0, 0, 0));
        tbl_desc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_desc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_descMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_desc);

        btn_add_desc.setMaximumSize(new java.awt.Dimension(35, 35));
        btn_add_desc.setMinimumSize(new java.awt.Dimension(35, 35));
        btn_add_desc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_descActionPerformed(evt);
            }
        });

        btn_rem_desc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rem_descActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_descricaoLayout = new javax.swing.GroupLayout(pn_descricao);
        pn_descricao.setLayout(pn_descricaoLayout);
        pn_descricaoLayout.setHorizontalGroup(
            pn_descricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_descricaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_descricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_add_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_rem_desc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pn_descricaoLayout.setVerticalGroup(
            pn_descricaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_descricaoLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btn_add_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_rem_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        pn_aplicacoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Aplicações"));
        pn_aplicacoes.setOpaque(false);

        tbl_aplic.setForeground(new java.awt.Color(0, 0, 0));
        tbl_aplic.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_aplic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_aplicMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_aplic);

        btn_add_aplic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_aplicActionPerformed(evt);
            }
        });

        btn_rem_aplic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rem_aplicActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_aplicacoesLayout = new javax.swing.GroupLayout(pn_aplicacoes);
        pn_aplicacoes.setLayout(pn_aplicacoesLayout);
        pn_aplicacoesLayout.setHorizontalGroup(
            pn_aplicacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_aplicacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(pn_aplicacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_add_aplic, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_rem_aplic, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        pn_aplicacoesLayout.setVerticalGroup(
            pn_aplicacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_aplicacoesLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btn_add_aplic, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_rem_aplic, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        pn_identificacao.setBorder(javax.swing.BorderFactory.createTitledBorder("Identificação"));
        pn_identificacao.setOpaque(false);

        lb_codigo.setText("Código Fabric.:");

        lb_nome.setText("Nome:");

        lb_localizacao.setText("Localização:");

        lb_tipo_produto.setText("Tipo:");

        tf_nome1.setEditable(false);

        jLabel1.setText("Código:");

        javax.swing.GroupLayout pn_identificacaoLayout = new javax.swing.GroupLayout(pn_identificacao);
        pn_identificacao.setLayout(pn_identificacaoLayout);
        pn_identificacaoLayout.setHorizontalGroup(
            pn_identificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_identificacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_identificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_nome)
                    .addComponent(lb_codigo)
                    .addComponent(lb_localizacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_identificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_identificacaoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(tf_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_nome1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(pn_identificacaoLayout.createSequentialGroup()
                        .addGroup(pn_identificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pn_identificacaoLayout.createSequentialGroup()
                                .addComponent(tf_localizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lb_tipo_produto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cb_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pn_identificacaoLayout.setVerticalGroup(
            pn_identificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_identificacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_identificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_codigo)
                    .addComponent(tf_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_nome1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_identificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_localizacao)
                    .addComponent(tf_localizacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_tipo_produto)
                    .addComponent(cb_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_identificacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_nome)
                    .addComponent(tf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pn_valores.setBorder(javax.swing.BorderFactory.createTitledBorder("Valores"));
        pn_valores.setOpaque(false);

        lb_custo.setText("Custo:");

        lb_valor_venda.setText("Valor Venda:");

        lb_qtdade.setText("Quantidade:");

        spn_qtdade.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(4.0f), null, null, Float.valueOf(1.0f)));

        javax.swing.GroupLayout pn_valoresLayout = new javax.swing.GroupLayout(pn_valores);
        pn_valores.setLayout(pn_valoresLayout);
        pn_valoresLayout.setHorizontalGroup(
            pn_valoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_valoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_custo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_custo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_valor_venda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_qtdade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spn_qtdade, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        pn_valoresLayout.setVerticalGroup(
            pn_valoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_valoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_valoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pn_valoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_custo)
                        .addComponent(lb_valor_venda)
                        .addComponent(tf_custo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tf_valor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lb_qtdade))
                    .addComponent(spn_qtdade))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pn_foto.setBorder(javax.swing.BorderFactory.createTitledBorder("Foto"));
        pn_foto.setOpaque(false);

        btn_escolher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_escolherActionPerformed(evt);
            }
        });

        btn_baixar_foto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_baixar_fotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_fotoLayout = new javax.swing.GroupLayout(pn_foto);
        pn_foto.setLayout(pn_fotoLayout);
        pn_fotoLayout.setHorizontalGroup(
            pn_fotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lb_foto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_fotoLayout.createSequentialGroup()
                .addGap(0, 80, Short.MAX_VALUE)
                .addComponent(btn_baixar_foto, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_escolher, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pn_fotoLayout.setVerticalGroup(
            pn_fotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_fotoLayout.createSequentialGroup()
                .addComponent(lb_foto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_fotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_escolher, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_baixar_foto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout pn_cadastro_produtoLayout = new javax.swing.GroupLayout(pn_cadastro_produto);
        pn_cadastro_produto.setLayout(pn_cadastro_produtoLayout);
        pn_cadastro_produtoLayout.setHorizontalGroup(
            pn_cadastro_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_cadastro_produtoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_cadastro_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_cadastro_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pn_cadastro_produtoLayout.createSequentialGroup()
                            .addComponent(pn_foto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pn_cadastro_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(pn_medidas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pn_identificacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(pn_cadastro_produtoLayout.createSequentialGroup()
                            .addComponent(pn_descricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(pn_aplicacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(pn_valores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_cadastro_produtoLayout.setVerticalGroup(
            pn_cadastro_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_cadastro_produtoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_cadastro_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pn_cadastro_produtoLayout.createSequentialGroup()
                        .addComponent(pn_identificacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pn_medidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pn_foto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_cadastro_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pn_aplicacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_descricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_valores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pn_cadastro_produto, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_cadastro_produto, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_escolherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_escolherActionPerformed
        getImage();
    }//GEN-LAST:event_btn_escolherActionPerformed

    private void btn_add_descActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_descActionPerformed
        newDescricao();
    }//GEN-LAST:event_btn_add_descActionPerformed

    private void btn_add_aplicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_aplicActionPerformed
        newAplicacao();
    }//GEN-LAST:event_btn_add_aplicActionPerformed

    private void btn_rem_descActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rem_descActionPerformed
        remSelectedDescricao();
    }//GEN-LAST:event_btn_rem_descActionPerformed

    private void tbl_descMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_descMouseClicked
        if (evt.getClickCount() == 2) {
            alterarDescricao();
        }
    }//GEN-LAST:event_tbl_descMouseClicked

    private void tbl_aplicMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_aplicMouseClicked
        if (evt.getClickCount() == 2) {
            alterarAplicacao();
        }
    }//GEN-LAST:event_tbl_aplicMouseClicked

    private void btn_rem_aplicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rem_aplicActionPerformed
        remSelectedAplicacao();
    }//GEN-LAST:event_btn_rem_aplicActionPerformed

    private void cb_unidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_unidadeActionPerformed
        unitType();
    }//GEN-LAST:event_cb_unidadeActionPerformed

    private void btn_baixar_fotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_baixar_fotoActionPerformed
        saveImage();
    }//GEN-LAST:event_btn_baixar_fotoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_aplic;
    private javax.swing.JButton btn_add_desc;
    private javax.swing.JButton btn_baixar_foto;
    private javax.swing.JButton btn_escolher;
    private javax.swing.JButton btn_rem_aplic;
    private javax.swing.JButton btn_rem_desc;
    private com.hermes.components.HMSelections.HMComboBox cb_tipo;
    private com.hermes.components.HMSelections.HMComboBox cb_unidade;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_codigo;
    private javax.swing.JLabel lb_custo;
    private javax.swing.JLabel lb_foto;
    private javax.swing.JLabel lb_localizacao;
    private javax.swing.JLabel lb_medida_externa;
    private javax.swing.JLabel lb_medida_interna;
    private javax.swing.JLabel lb_medida_interna1;
    private javax.swing.JLabel lb_nome;
    private javax.swing.JLabel lb_qtdade;
    private javax.swing.JLabel lb_tipo_produto;
    private javax.swing.JLabel lb_unidade;
    private javax.swing.JLabel lb_valor_venda;
    private javax.swing.JPanel pn_aplicacoes;
    private javax.swing.JPanel pn_cadastro_produto;
    private javax.swing.JPanel pn_descricao;
    private javax.swing.JPanel pn_foto;
    private javax.swing.JPanel pn_identificacao;
    private javax.swing.JPanel pn_medidas;
    private javax.swing.JPanel pn_valores;
    private javax.swing.JSpinner spn_qtdade;
    private com.hermes.components.HMTables.HMTable tbl_aplic;
    private com.hermes.components.HMTables.HMTable tbl_desc;
    private com.hermes.components.HMFields.HMDecimalField tf_altura;
    private com.hermes.components.HMFields.HMTextField tf_codigo;
    private com.hermes.components.HMFields.HMMonetaryField tf_custo;
    private com.hermes.components.HMFields.HMTextField tf_localizacao;
    private com.hermes.components.HMFields.HMDecimalField tf_med_externa;
    private com.hermes.components.HMFields.HMDecimalField tf_med_interna;
    private com.hermes.components.HMFields.HMTextField tf_nome;
    private com.hermes.components.HMFields.HMTextField tf_nome1;
    private com.hermes.components.HMFields.HMMonetaryField tf_valor;
    // End of variables declaration//GEN-END:variables
}
