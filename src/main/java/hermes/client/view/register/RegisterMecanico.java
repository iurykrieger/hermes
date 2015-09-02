package hermes.client.view.register;

import hermes.client.view.search.SearchMecanico;
import com.hermes.components.HMFields.HMFormattedTextField;
import com.hermes.components.HMFrames.HMPanel;
import com.hermes.components.utils.ImageFactory;
import hermes.client.controll.mecanico.MecanicoController;
import hermes.client.controll.utils.EstadosController;
import hermes.client.view.frame.Desktop;
import hermes.ejb.entidades.Estados;
import hermes.ejb.entidades.Mecanico;
import hermes.ejb.entidades.MecanicoInfo;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author iury
 */
public class RegisterMecanico extends HMPanel {

    private Mecanico mecanico;
    private MecanicoInfo mecanicoInfo;
    private int index = -1;
    private SearchMecanico frame;

    public void initFields() {
        //TEXT FIELDS
        tf_celular.setMask(HMFormattedTextField.TELEFONE);
        tf_cep.setObrigatory(true);
        tf_cep.setMask(HMFormattedTextField.CEP);
        tf_cpf.setObrigatory(true);
        tf_cpf.setMask(HMFormattedTextField.CPF);
        tf_data_nasc.setMask(HMFormattedTextField.DATA);
        tf_data_nasc.setObrigatory(true);
        tf_endereco.setObrigatory(true);
        tf_nome.setObrigatory(true);
        tf_telefone.setObrigatory(true);
        tf_telefone.setMask(HMFormattedTextField.TELEFONE);
        //COMBO BOXES
        String sexo[] = {"Masculino", "Feminino"};
        cb_sexo.setValues(sexo);
        cb_estado.setValues(new EstadosController().getEstados());
        verificaEstado();
        //BUTTONS
        btn_escolher1.setIcon(new ImageFactory().getIcon("open_folder_add.png", 25, 25));
    }

    public void verificaEstado() {
        cb_cidade.removeAllItems();
        cb_cidade.setValues(new EstadosController().getCidadesByEstado(((Estados) cb_estado.getSelectedItem())));
    }

    public void abrirFileChooser() {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ImageIcon i = new ImageIcon(file.getAbsolutePath());
                i.setImage(i.getImage().getScaledInstance(pn_foto1.getWidth(), pn_foto1.getHeight(), 100));
                lb_foto1.setIcon(i);
            } catch (Exception ex) {
                System.out.println("problem accessing file" + file.getAbsolutePath());
            }
        } else {
            System.out.println("File access cancelled by user.");
        }
    }

    public void cadastraMecanico() {
        try {
            if (verificaCampos()) {
                MecanicoInfo mecanicoInfo = new MecanicoInfo();
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                mecanico.setNome(tf_nome.getText());
                mecanico.setCpf(tf_cpf.getText());
                mecanico.setSexo(cb_sexo.getSelectedItem().toString());
                mecanico.setDataNascimento(df.parse(tf_data_nasc.getText()));
                mecanicoInfo.setCelular(tf_celular.getText());
                mecanicoInfo.setCep(tf_cep.getText());
                mecanicoInfo.setCidade(cb_cidade.getSelectedItem().toString());
                mecanicoInfo.setEmail(tf_email.getText());
                mecanicoInfo.setEndereco(tf_endereco.getText());
                mecanicoInfo.setEnderecoDescricao(tf_desc_end.getText());
                mecanicoInfo.setEstado(cb_estado.getSelectedItem().toString());
                mecanicoInfo.setIdAtivo(true);
                mecanicoInfo.setTelefone(tf_telefone.getText());
                mecanicoInfo.setDthAlteracao(new Date(System.currentTimeMillis()));
                Double value = Double.valueOf(spn_carga_horaria.getValue().toString());
                mecanicoInfo.setCargaHoraria(BigDecimal.valueOf(value));
                mecanicoInfo.setValorHora(tf_valor_mao_obra.getValue());
                mecanico = new MecanicoController().cadastraMecanico(mecanico);
                new MecanicoController().setMecanicoInfosInactive(mecanico);
                mecanicoInfo.setIdMecanico(mecanico);
                mecanicoInfo = new MecanicoController().cadastraMecanicoInfo(mecanicoInfo);
                JOptionPane.showMessageDialog(this, "Mecanico salvo com sucesso!");
                if (index == -1) {
                    this.resetaPanel(this);
                    this.mecanico = new Mecanico();
                    this.mecanicoInfo = new MecanicoInfo();
                } else {
                    Desktop.desktop_pane.getSelectedFrame().dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Todos os campos obrigatórios devem ser preenchidos!");
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Não foi possível salvar o mecanico!");
        }
    }

    public void buildCliente() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        tf_nome.setText(this.mecanico.getNome());
        tf_cpf.setText(this.mecanico.getCpf());
        tf_data_nasc.setText(df.format(this.mecanico.getDataNascimento()));
        tf_telefone.setText(this.mecanicoInfo.getTelefone());
        tf_celular.setText(this.mecanicoInfo.getCelular());
        tf_email.setText(this.mecanicoInfo.getEmail());
        tf_endereco.setText(this.mecanicoInfo.getEndereco());
        tf_desc_end.setText(this.mecanicoInfo.getEnderecoDescricao());
        tf_cep.setText(this.mecanicoInfo.getCep());
        cb_sexo.setSelectedItem(this.mecanico.getSexo());
        cb_cidade.setSelectedItem(this.mecanicoInfo.getCidade());
        cb_estado.setSelectedItem(this.mecanicoInfo.getEstado());
        spn_carga_horaria.setValue(this.mecanicoInfo.getCargaHoraria());
        tf_valor_mao_obra.setValue(this.mecanicoInfo.getValorHora());
        tf_nome.setValid(true);
        tf_cpf.setValid(true);
        tf_data_nasc.setValid(true);
        tf_telefone.setValid(true);
        tf_celular.setValid(true);
        tf_email.setValid(true);
        tf_endereco.setValid(true);
        tf_desc_end.setValid(true);
        tf_cep.setValid(true);
        setSize(681, 538);
    }

    public boolean verificaCampos() {
        if (tf_nome.isValid() && tf_cpf.isValid() && tf_data_nasc.isValid() && tf_telefone.isValid() && tf_celular.isValid() && tf_email.isValid()
                && tf_endereco.isValid() && tf_desc_end.isValid() && tf_numero.isValid() && tf_cep.isValid()) {
            return true;
        } else {
            return false;
        }
    }

    public RegisterMecanico() {
        this.mecanicoInfo = new MecanicoInfo();
        this.mecanico = new Mecanico();
        this.index = -1;
        initComponents();
        setSize(681, 467);
        initFields();
    }

    public RegisterMecanico(MecanicoInfo mi, SearchMecanico frame, int index) {
        this.mecanicoInfo = mi;
        this.index = index;
        this.mecanico = mi.getIdMecanico();
        this.frame = frame;
        initComponents();
        setSize(681, 438);
        initFields();
        buildCliente();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        pn_cadastro_mecanico = new javax.swing.JPanel();
        pn_contato = new javax.swing.JPanel();
        lb_telefone = new javax.swing.JLabel();
        lb_celular = new javax.swing.JLabel();
        lb_email = new javax.swing.JLabel();
        tf_telefone = new com.hermes.components.HMFields.HMFormattedTextField();
        tf_celular = new com.hermes.components.HMFields.HMFormattedTextField();
        tf_email = new com.hermes.components.HMFields.HMTextField();
        pn_dados_pessoais = new javax.swing.JPanel();
        lb_nome = new javax.swing.JLabel();
        lb_sexo = new javax.swing.JLabel();
        lb_cpf = new javax.swing.JLabel();
        lb_data_nasc = new javax.swing.JLabel();
        tf_nome = new com.hermes.components.HMFields.HMTextField();
        tf_cpf = new com.hermes.components.HMFields.HMFormattedTextField();
        cb_sexo = new com.hermes.components.HMSelections.HMComboBox();
        tf_data_nasc = new com.hermes.components.HMFields.HMFormattedTextField();
        pn_jornada_trabalho = new javax.swing.JPanel();
        lb_carga_horaria = new javax.swing.JLabel();
        lb_valor_mao_obra = new javax.swing.JLabel();
        tf_valor_mao_obra = new com.hermes.components.HMFields.HMMonetaryField();
        spn_carga_horaria = new javax.swing.JSpinner();
        pn_foto1 = new javax.swing.JPanel();
        btn_escolher1 = new javax.swing.JButton();
        lb_foto1 = new javax.swing.JLabel();
        pn_localizacao = new javax.swing.JPanel();
        lb_estado = new javax.swing.JLabel();
        lb_cidade = new javax.swing.JLabel();
        lb_endereco = new javax.swing.JLabel();
        lb_numero = new javax.swing.JLabel();
        lb_cep = new javax.swing.JLabel();
        lb_descricao = new javax.swing.JLabel();
        cb_estado = new com.hermes.components.HMSelections.HMComboBox();
        cb_cidade = new com.hermes.components.HMSelections.HMComboBox();
        tf_numero = new com.hermes.components.HMFields.HMTextField();
        tf_endereco = new com.hermes.components.HMFields.HMTextField();
        tf_desc_end = new com.hermes.components.HMFields.HMTextField();
        tf_cep = new com.hermes.components.HMFields.HMFormattedTextField();
        sv_button = new javax.swing.JButton();

        setOpaque(false);

        pn_cadastro_mecanico.setOpaque(false);

        pn_contato.setBorder(javax.swing.BorderFactory.createTitledBorder("Contato"));
        pn_contato.setOpaque(false);

        lb_telefone.setText("Telefone:");

        lb_celular.setText("Celular:");

        lb_email.setText("E-mail:");

        javax.swing.GroupLayout pn_contatoLayout = new javax.swing.GroupLayout(pn_contato);
        pn_contato.setLayout(pn_contatoLayout);
        pn_contatoLayout.setHorizontalGroup(
            pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_contatoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pn_contatoLayout.createSequentialGroup()
                        .addComponent(lb_email)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tf_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_contatoLayout.createSequentialGroup()
                        .addComponent(lb_telefone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tf_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lb_celular)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );
        pn_contatoLayout.setVerticalGroup(
            pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_contatoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_telefone)
                    .addComponent(lb_celular)
                    .addComponent(tf_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_contatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_email)
                    .addComponent(tf_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pn_dados_pessoais.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Pessoais"));
        pn_dados_pessoais.setOpaque(false);

        lb_nome.setText("Nome:");

        lb_sexo.setText("Sexo:");

        lb_cpf.setText("CPF:");

        lb_data_nasc.setText("Data Nasc.:");

        javax.swing.GroupLayout pn_dados_pessoaisLayout = new javax.swing.GroupLayout(pn_dados_pessoais);
        pn_dados_pessoais.setLayout(pn_dados_pessoaisLayout);
        pn_dados_pessoaisLayout.setHorizontalGroup(
            pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dados_pessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_nome)
                    .addComponent(lb_cpf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_data_nasc)
                    .addComponent(lb_sexo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cb_sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_data_nasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        pn_dados_pessoaisLayout.setVerticalGroup(
            pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dados_pessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_nome)
                    .addComponent(lb_sexo)
                    .addComponent(tf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(pn_dados_pessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_cpf)
                    .addComponent(lb_data_nasc)
                    .addComponent(tf_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_data_nasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        pn_jornada_trabalho.setBorder(javax.swing.BorderFactory.createTitledBorder("Jornada de Trabalho"));
        pn_jornada_trabalho.setOpaque(false);

        lb_carga_horaria.setText("Carga Horária Diária: ");

        lb_valor_mao_obra.setText("Valor Mão de Obra:");

        javax.swing.GroupLayout pn_jornada_trabalhoLayout = new javax.swing.GroupLayout(pn_jornada_trabalho);
        pn_jornada_trabalho.setLayout(pn_jornada_trabalhoLayout);
        pn_jornada_trabalhoLayout.setHorizontalGroup(
            pn_jornada_trabalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_jornada_trabalhoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_carga_horaria)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spn_carga_horaria, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(lb_valor_mao_obra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tf_valor_mao_obra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_jornada_trabalhoLayout.setVerticalGroup(
            pn_jornada_trabalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_jornada_trabalhoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_jornada_trabalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_jornada_trabalhoLayout.createSequentialGroup()
                        .addGroup(pn_jornada_trabalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_carga_horaria)
                            .addComponent(tf_valor_mao_obra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_valor_mao_obra))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(spn_carga_horaria))
                .addContainerGap())
        );

        pn_foto1.setBorder(javax.swing.BorderFactory.createTitledBorder("Foto"));
        pn_foto1.setOpaque(false);

        btn_escolher1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_escolher1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_foto1Layout = new javax.swing.GroupLayout(pn_foto1);
        pn_foto1.setLayout(pn_foto1Layout);
        pn_foto1Layout.setHorizontalGroup(
            pn_foto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_foto1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_escolher1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(lb_foto1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pn_foto1Layout.setVerticalGroup(
            pn_foto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_foto1Layout.createSequentialGroup()
                .addComponent(lb_foto1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_escolher1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pn_localizacao.setBorder(javax.swing.BorderFactory.createTitledBorder("Locallização"));
        pn_localizacao.setOpaque(false);

        lb_estado.setText("Estado:");
        lb_estado.setMaximumSize(new java.awt.Dimension(29, 14));
        lb_estado.setMinimumSize(new java.awt.Dimension(29, 14));

        lb_cidade.setText("Cidade:");

        lb_endereco.setText("Endereço:");

        lb_numero.setText("Número:");

        lb_cep.setText("CEP:");

        lb_descricao.setText("Desc. End.:");

        cb_estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_estadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_localizacaoLayout = new javax.swing.GroupLayout(pn_localizacao);
        pn_localizacao.setLayout(pn_localizacaoLayout);
        pn_localizacaoLayout.setHorizontalGroup(
            pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_localizacaoLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_endereco, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_estado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tf_endereco, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(cb_estado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_localizacaoLayout.createSequentialGroup()
                        .addComponent(lb_cidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(pn_localizacaoLayout.createSequentialGroup()
                        .addComponent(lb_descricao)
                        .addGap(5, 5, 5)))
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cb_cidade, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(tf_desc_end, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_numero)
                    .addComponent(lb_cep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_cep, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(tf_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                .addContainerGap())
        );
        pn_localizacaoLayout.setVerticalGroup(
            pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_localizacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_cidade)
                    .addComponent(lb_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_cidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_numero)
                    .addComponent(tf_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_localizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_endereco)
                    .addComponent(tf_endereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_descricao)
                    .addComponent(tf_desc_end, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_cep)
                    .addComponent(tf_cep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        sv_button.setText("Salvar");
        sv_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sv_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_cadastro_mecanicoLayout = new javax.swing.GroupLayout(pn_cadastro_mecanico);
        pn_cadastro_mecanico.setLayout(pn_cadastro_mecanicoLayout);
        pn_cadastro_mecanicoLayout.setHorizontalGroup(
            pn_cadastro_mecanicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_cadastro_mecanicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_cadastro_mecanicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_cadastro_mecanicoLayout.createSequentialGroup()
                        .addComponent(pn_foto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pn_cadastro_mecanicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pn_dados_pessoais, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pn_contato, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(pn_localizacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_jornada_trabalho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_cadastro_mecanicoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(sv_button, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pn_cadastro_mecanicoLayout.setVerticalGroup(
            pn_cadastro_mecanicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_cadastro_mecanicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_cadastro_mecanicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pn_cadastro_mecanicoLayout.createSequentialGroup()
                        .addComponent(pn_dados_pessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pn_contato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pn_foto1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addComponent(pn_localizacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_jornada_trabalho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sv_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_cadastro_mecanico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_cadastro_mecanico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cb_estadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_estadoActionPerformed
        verificaEstado();
    }//GEN-LAST:event_cb_estadoActionPerformed

    private void btn_escolher1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_escolher1ActionPerformed
        abrirFileChooser();
    }//GEN-LAST:event_btn_escolher1ActionPerformed

    private void sv_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sv_buttonActionPerformed
        cadastraMecanico();
    }//GEN-LAST:event_sv_buttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_escolher1;
    private com.hermes.components.HMSelections.HMComboBox cb_cidade;
    private com.hermes.components.HMSelections.HMComboBox cb_estado;
    private com.hermes.components.HMSelections.HMComboBox cb_sexo;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel lb_carga_horaria;
    private javax.swing.JLabel lb_celular;
    private javax.swing.JLabel lb_cep;
    private javax.swing.JLabel lb_cidade;
    private javax.swing.JLabel lb_cpf;
    private javax.swing.JLabel lb_data_nasc;
    private javax.swing.JLabel lb_descricao;
    private javax.swing.JLabel lb_email;
    private javax.swing.JLabel lb_endereco;
    private javax.swing.JLabel lb_estado;
    private javax.swing.JLabel lb_foto1;
    private javax.swing.JLabel lb_nome;
    private javax.swing.JLabel lb_numero;
    private javax.swing.JLabel lb_sexo;
    private javax.swing.JLabel lb_telefone;
    private javax.swing.JLabel lb_valor_mao_obra;
    private javax.swing.JPanel pn_cadastro_mecanico;
    private javax.swing.JPanel pn_contato;
    private javax.swing.JPanel pn_dados_pessoais;
    private javax.swing.JPanel pn_foto1;
    private javax.swing.JPanel pn_jornada_trabalho;
    private javax.swing.JPanel pn_localizacao;
    private javax.swing.JSpinner spn_carga_horaria;
    private javax.swing.JButton sv_button;
    private com.hermes.components.HMFields.HMFormattedTextField tf_celular;
    private com.hermes.components.HMFields.HMFormattedTextField tf_cep;
    private com.hermes.components.HMFields.HMFormattedTextField tf_cpf;
    private com.hermes.components.HMFields.HMFormattedTextField tf_data_nasc;
    private com.hermes.components.HMFields.HMTextField tf_desc_end;
    private com.hermes.components.HMFields.HMTextField tf_email;
    private com.hermes.components.HMFields.HMTextField tf_endereco;
    private com.hermes.components.HMFields.HMTextField tf_nome;
    private com.hermes.components.HMFields.HMTextField tf_numero;
    private com.hermes.components.HMFields.HMFormattedTextField tf_telefone;
    private com.hermes.components.HMFields.HMMonetaryField tf_valor_mao_obra;
    // End of variables declaration//GEN-END:variables
}
