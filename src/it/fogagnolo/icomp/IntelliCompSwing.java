
package it.fogagnolo.icomp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 * JTabbedPane Demo
 * 
 * @version 1.11 11/17/05
 * @author Jeff Dinkins
 */
@SuppressWarnings("serial")
@DemoProperties(value = "JTabbedPane Demo", category = "Containers", description = "Demonstrates JTabbedPane, a container which allows tabbed navigation of components", sourceFiles = {
        "it/fogagnolo/icomp/IntelliCompSwing.java", "it/fogagnolo/icomp/ResourceManager.java",
        "it/fogagnolo/icomp/resources/IntelliCompSwing.properties", "it/fogagnolo/icomp/resources/images/stellina.gif",
        "it/fogagnolo/icomp/resources/images/fiore.gif", "it/fogagnolo/icomp/resources/images/camille.jpg",
        "it/fogagnolo/icomp/resources/images/david.gif", "it/fogagnolo/icomp/resources/images/ewan.gif",
        "it/fogagnolo/icomp/resources/images/ewan.jpg", "it/fogagnolo/icomp/resources/images/miranda.jpg",
        "it/fogagnolo/icomp/resources/images/matthew.gif", "it/fogagnolo/icomp/resources/images/stephen.gif",
        "it/fogagnolo/icomp/resources/images/IntelliCompSwing.gif" })
public class IntelliCompSwing extends JPanel implements ActionListener {

    private final ResourceManager resourceManager = new ResourceManager(this.getClass());

    // private final HeadSpin spin;

    private final JTabbedPane     tabbedpane;

    private final ButtonGroup     group;

    private final JRadioButton    top;
    private final JRadioButton    bottom;
    private final JRadioButton    left;
    private final JRadioButton    right;

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {

        JFrame frame = new JFrame(IntelliCompSwing.class.getAnnotation(DemoProperties.class).value());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new IntelliCompSwing());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * IntelliCompSwing Constructor
     */
    public IntelliCompSwing() {

        setLayout(new BorderLayout());

        // create tab position controls
        JPanel tabControls = new JPanel();
        tabControls.add(new JLabel(resourceManager.getString("IntelliCompSwing.label")));
        top = (JRadioButton) tabControls.add(new JRadioButton(resourceManager.getString("IntelliCompSwing.top")));
        left = (JRadioButton) tabControls.add(new JRadioButton(resourceManager.getString("IntelliCompSwing.left")));
        bottom = (JRadioButton) tabControls.add(new JRadioButton(resourceManager.getString("IntelliCompSwing.bottom")));
        right = (JRadioButton) tabControls.add(new JRadioButton(resourceManager.getString("IntelliCompSwing.right")));
        add(tabControls, BorderLayout.NORTH);

        group = new ButtonGroup();
        // group.add(top);
        // group.add(bottom);
        // group.add(left);
        // group.add(right);
        //
        // top.setSelected(true);
        //
        // top.addActionListener(this);
        // bottom.addActionListener(this);
        // left.addActionListener(this);
        // right.addActionListener(this);

        // create tab
        tabbedpane = new JTabbedPane();
        add(tabbedpane, BorderLayout.CENTER);

        drawFinder();
        drawCleaner();
        drawResult();

        String name = resourceManager.getString("IntelliCompSwing.camille");
        JLabel pix = new JLabel(resourceManager.createImageIcon("camille.jpg", name));
        tabbedpane.add(name, pix);

        name = resourceManager.getString("IntelliCompSwing.miranda");
        pix = new JLabel(resourceManager.createImageIcon("miranda.jpg", name));
        pix.setToolTipText(resourceManager.getString("IntelliCompSwing.miranda.tooltip"));
        tabbedpane.add(name, pix);

        name = resourceManager.getString("IntelliCompSwing.ewan");
        pix = new JLabel(resourceManager.createImageIcon("ewan.jpg", name));
        tabbedpane.add(name, pix);

        // name = resourceManager.getString("IntelliCompSwing.bounce");
        // spin = new HeadSpin();
        // tabbedpane.add(name, spin);

        // tabbedpane.getModel().addChangeListener(new ChangeListener() {
        //
        // public void stateChanged(ChangeEvent e) {
        //
        // SingleSelectionModel model = (SingleSelectionModel) e.getSource();
        // if (model.getSelectedIndex() == tabbedpane.getTabCount() - 1) {
        // spin.go();
        // }
        // }    
        // });
    }

    private void drawFinder() {

        JPanel jp = new JPanel();
        LayoutManager layout = new FlowLayout(FlowLayout.LEFT);
        jp.setLayout(layout);

        jp.add(new JLabel(resourceManager.getString("IComp.finder.root")));
        JTextField root = (JTextField) jp.add(new JTextField(50));
        jp.add(new JLabel(resourceManager.getString("IComp.finder.exclude")));
        JTextField exclude = (JTextField) jp.add(new JTextField(50));
        jp.add(new JLabel(resourceManager.getString("IComp.finder.filter")));
        JTextField filter = (JTextField) jp.add(new JTextField(50));
        JCheckBox debug = (JCheckBox) jp.add(new JCheckBox(resourceManager.getString("IComp.finder.debug")));
        jp.add(new JLabel(resourceManager.getString("IComp.finder.offset")));
        JTextField offset = (JTextField) jp.add(new JTextField(
                resourceManager.getString("IComp.finder.offset.default"), 5));
        jp.add(new JLabel(resourceManager.getString("IComp.finder.length")));
        JTextField length = (JTextField) jp.add(new JTextField(
                resourceManager.getString("IComp.finder.length.default"), 5));
        jp.add(new JLabel(resourceManager.getString("IComp.finder.contentLength")));
        JTextField contentLength = (JTextField) jp.add(new JTextField(resourceManager
                .getString("IComp.finder.contentLength.default"), 5));
        jp.add(new JLabel(resourceManager.getString("IComp.finder.bufferSize")));
        JTextField bufferSize = (JTextField) jp.add(new JTextField(resourceManager
                .getString("IComp.finder.bufferSize.default"), 5));
        jp.add(new JLabel(resourceManager.getString("IComp.finder.inputFile")));
        JTextField inputFile = (JTextField) jp.add(new JTextField(50));
        jp.add(new JLabel(resourceManager.getString("IComp.finder.saveFile")));
        JTextField saveFile = (JTextField) jp.add(new JTextField(50));

        // compare
        jp.add(new JLabel(resourceManager.getString("IComp.finder.compare")));
        JRadioButton compareName = (JRadioButton) jp.add(new JRadioButton(resourceManager
                .getString("IComp.finder.compare.name")));
        JRadioButton compareSize = (JRadioButton) jp.add(new JRadioButton(resourceManager
                .getString("IComp.finder.compare.size")));
        JRadioButton compareInitialDigest = (JRadioButton) jp.add(new JRadioButton(resourceManager
                .getString("IComp.finder.compare.initialDigest")));
        JRadioButton compareFastDigest = (JRadioButton) jp.add(new JRadioButton(resourceManager
                .getString("IComp.finder.compare.fastDigest")));
        JRadioButton compareFullDigest = (JRadioButton) jp.add(new JRadioButton(resourceManager
                .getString("IComp.finder.compare.fullDigest")));
//        add(compareName, BorderLayout.WEST);
//        add(compareSize, BorderLayout.WEST);
//        add(compareInitialDigest, BorderLayout.WEST);
//        add(compareFastDigest, BorderLayout.WEST);
//        add(compareFullDigest, BorderLayout.WEST);

        ButtonGroup compareGroup = new ButtonGroup();
        compareGroup.add(compareName);
        compareGroup.add(compareSize);
        compareGroup.add(compareInitialDigest);
        compareGroup.add(compareFastDigest);
        compareGroup.add(compareFullDigest);
        compareFastDigest.setSelected(true);

        JCheckBox symlink = (JCheckBox) jp.add(new JCheckBox(resourceManager.getString("IComp.finder.followsymlink")));

        tabbedpane.add(resourceManager.getString("IComp.finder"), jp);

    }

    private void drawResult() {

        JPanel jp = new JPanel();

        // jp.add(new JFrame("prova frame"));

        // TODO Auto-generated method stub

        tabbedpane.add(resourceManager.getString("IComp.result"), jp);

    }

    private void drawCleaner() {

        JPanel jp = new JPanel();

        // TODO Auto-generated method stub

        tabbedpane.add(resourceManager.getString("IComp.cleaner"), jp);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == top) {
            tabbedpane.setTabPlacement(JTabbedPane.TOP);
        } else if (e.getSource() == left) {
            tabbedpane.setTabPlacement(JTabbedPane.LEFT);
        } else if (e.getSource() == bottom) {
            tabbedpane.setTabPlacement(JTabbedPane.BOTTOM);
        } else if (e.getSource() == right) {
            tabbedpane.setTabPlacement(JTabbedPane.RIGHT);
        }
    }
    //
    // private class HeadSpin extends JComponent implements ActionListener {
    //
    // private javax.swing.Timer animator;
    //
    // private final ImageIcon[] icon = new ImageIcon[6];
    //
    // private final static int numImages = 6;
    //
    // private final double[] x = new double[numImages];
    // private final double[] y = new double[numImages];
    //
    // private final int[] xh = new int[numImages];
    // private final int[] yh = new int[numImages];
    //
    // private final double[] scale = new double[numImages];
    //
    // private final Random rand = new Random();
    //
    // public HeadSpin() {
    //
    // setBackground(Color.black);
    // icon[0] = resourceManager.createImageIcon("ewan.gif", resourceManager.getString("IntelliCompSwing.ewan"));
    // icon[1] = resourceManager.createImageIcon("stephen.gif", resourceManager
    // .getString("IntelliCompSwing.stephen"));
    // icon[2] = resourceManager.createImageIcon("david.gif", resourceManager.getString("IntelliCompSwing.david"));
    // icon[3] = resourceManager.createImageIcon("matthew.gif", resourceManager
    // .getString("IntelliCompSwing.matthew"));
    // icon[4] = resourceManager.createImageIcon("blake.gif", resourceManager.getString("IntelliCompSwing.blake"));
    // icon[5] = resourceManager.createImageIcon("brooke.gif", resourceManager
    // .getString("IntelliCompSwing.brooke"));
    //
    // /*
    // * for(int i = 0; i < 6; i++) { x[i] = (double) rand.nextInt(500); y[i] = (double) rand.nextInt(500); }
    // */
    // }
    //
    // public void go() {
    //
    // animator = new javax.swing.Timer(22 + 22 + 22, this);
    // animator.start();
    // }
    //
    // public void paint(Graphics g) {
    //
    // g.setColor(getBackground());
    // g.fillRect(0, 0, getWidth(), getHeight());
    //
    // for (int i = 0; i < numImages; i++) {
    // if (x[i] > 3 * i) {
    // nudge(i);
    // squish(g, icon[i], xh[i], yh[i], scale[i]);
    // } else {
    // x[i] += .05;
    // y[i] += .05;
    // }
    // }
    // }
    //
    // public void nudge(int i) {
    //
    // x[i] += (double) rand.nextInt(1000) / 8756;
    // y[i] += (double) rand.nextInt(1000) / 5432;
    // int tmpScale = (int) (Math.abs(Math.sin(x[i])) * 10);
    // scale[i] = (double) tmpScale / 10;
    // int nudgeX = (int) (((double) getWidth() / 2) * .8);
    // int nudgeY = (int) (((double) getHeight() / 2) * .60);
    // xh[i] = (int) (Math.sin(x[i]) * nudgeX) + nudgeX;
    // yh[i] = (int) (Math.sin(y[i]) * nudgeY) + nudgeY;
    // }
    //
    // public void squish(Graphics g, ImageIcon icon, int x, int y, double scale) {
    //
    // if (isVisible()) {
    // g.drawImage(icon.getImage(), x, y, (int) (icon.getIconWidth() * scale),
    // (int) (icon.getIconHeight() * scale), this);
    // }
    // }
    //
    // public void actionPerformed(ActionEvent e) {
    //
    // if (isVisible()) {
    // repaint();
    // } else {
    // animator.stop();
    // }
    // }
    // }
}
