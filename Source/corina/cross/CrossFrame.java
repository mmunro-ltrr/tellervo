//
// This file is part of Corina.
// 
// Corina is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// Corina is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Corina; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// Copyright 2001 Ken Harris <kbh7@cornell.edu>
//

package corina.cross;

import corina.Year;
import corina.Element;
import corina.graph.GraphFrame;
import corina.site.Site;
import corina.site.SiteDB;
import corina.map.MapFrame;
import corina.gui.JarIcon;
import corina.gui.XFrame;
import corina.gui.XMenubar;
import corina.gui.PrintableDocument;
import corina.gui.HasPreferences;
import corina.gui.Tree;
import corina.gui.ButtonLayout;

import java.io.IOException;

import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import java.text.DecimalFormat;

import java.awt.*; // !!!
import java.awt.print.Printable;
import java.awt.print.PageFormat;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*; // !!!
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
   A crossdate.  Displays all scores and significant scores, and lets
   the user step forward/backward through the Sequence, graphing any
   desired cross.

   @author <a href="mailto:kbh7@cornell.edu">Ken Harris</a>
   @version $Id$
*/

/*
  current design is ok, but needs improving:
  - above tabs, add:

  Fixed:   [tree] title of fixed (range)     ( Swap  )
  Moving:  [tree] title of moving (range)    ( These )
*/

public class CrossFrame extends XFrame implements PrintableDocument, HasPreferences {

    // gui
    private JTable scoresTable=null, sigsTable, histogram;
    private JTabbedPane tabPane;
    private JButton prevButton, nextButton, graphButton, mapButton, closeButton;
    private Timer timer;

    // l10n
    private ResourceBundle msg = ResourceBundle.getBundle("CrossdatingBundle");

    // data
    private Sequence seq;
    private Cross cross=null;

    // titles on top
    private JLabel fixedTitle;
    private JLabel movingTitle;
    private Tree fixedTree;
    private Tree movingTree;

    /** A table model for displaying all the scores in a Cross. */
    public class CrossTableModel extends AbstractTableModel {
	// cross to display
	private Cross c;

	// range of rows
	private int row_min, row_max;

	/** Create a TableModel to display the given Cross.
	    @param c the Cross to be displayed */
	public CrossTableModel(Cross c) {
	    // copy data
	    this.c = c;

	    // compute range of rows
	    row_min = c.range.getStart().row();
	    row_max = c.range.getEnd().row();
	}

	/** The column name: "Year" for the first column, and a digit (0-9) for the others.
	    @param col column number to query
	    @return the column name */
	public String getColumnName(int col) {
	    if (col == 0)
		return msg.getString("year");
	    else
		return Integer.toString(col-1);
	}

	/** The number of rows.
	    @return the number of rows */
	public int getRowCount() {
	    return (row_max - row_min + 1);
	}

	/** The number of columns.
	    @return the number of columns */
	public int getColumnCount() {
	    return 11;
	}

	/** Compute the year of a particular (row, col) cell.
	    @param row the cell's row
	    @param col the cell's column
	    @return the Year of that cell */
	protected Year getYear(int row, int col) {
	    return new Year(10 * (row + row_min) + col - 1);
	}

	/** The value at a (row, col) location.  The crossdate score,
	    except the lefthand column, which is the decade label.
	    @param row row number to query
	    @param col column number to query
	    @return the score at that year, or the decade label */
	public Object getValueAt(int row, int col) {
	    if (col == 0) {
		if (row == 0)
		    return c.range.getStart();
		else if (row + row_min == 0)
		    return new Year(1); // special case
		else
		    return getYear(row, col+1);
	    } else {
		Year y = getYear(row, col);
		if (!c.range.contains(y))
		    return null;
		else
		    return new Double(c.data[y.diff(c.range.getStart())]);
	    }
	}
    }

    /**
       A table model for displaying the statistically significant scores
       in a Cross.
    */
    private class CrossSigsTableModel extends AbstractTableModel {
	// the cross to display
	private Cross c;

	// formatter for the scores
	private DecimalFormat df;

	/** Create a Tablemodel to display the significant scores of a
	    given Cross.
	    @param c the Cross to be displayed */
	public CrossSigsTableModel(Cross c) {
	    // copy data
	    this.c = c;

	    df = new DecimalFormat(c.getFormat());
	}

	public void formatChanged() {
	    df = new DecimalFormat(c.getFormat());
	}

	/** The column name.  The columns are:
	    <ul>
	    <li>Nr.
	    <li>Fixed (f<sub>start</sub> - f<sub>end</sub>)
	    <li>Moving (m<sub>start</sub> - m<sub>end</sub>)
	    <li>Algorithm
	    <li>Overlap
	    </ul>
	    @param col the column to query
	    @return the column's name */
	public String getColumnName(int col) {
	    switch (col) {
	    case 0: return msg.getString("number");
	    case 1: return msg.getString("fixed") + " (" + c.fixed.range + ")";
	    case 2: return msg.getString("moving") + " (" + c.moving.range + ")";
	    case 3: return c.getName();
	    case 4: return msg.getString("overlap");
	    default: return null;
	    }
	}

	/** The number of rows.
	    @return the number of rows */
	public int getRowCount() {
	    return (c==null ? 0 : c.highScores.size());
	}

	/** The number of columns.
	    @return the number of columns */
	public int getColumnCount() {
	    return 5; // nr, fixed, moving, score, overlap
	}

	/** The class of a given column.  This is used so the default
	    table renderer right-aligns Integers.
	    @param col the column to query
	    @return Class of this column's data */
	public Class getColumnClass(int col) {
	    return getValueAt(0, col).getClass();
	}

	/** The value at a (row, col) location.
	    @param row row number to query
	    @param col column number to query
	    @return the cell's value */
	public Object getValueAt(int row, int col) {
	    if (c == null)
		return null;

	    switch (col) {
	    case 0: return new Integer(((Score) c.highScores.get(row)).number);
	    case 1: return ((Score) c.highScores.get(row)).fixedRange;
	    case 2: return ((Score) c.highScores.get(row)).movingRange;
	    case 3: return df.format(((Score) c.highScores.get(row)).score);
	    case 4: return new Integer(((Score) c.highScores.get(row)).span);
	    default: return null;
	    }
	}
    }

    public class CountRenderer extends JProgressBar implements TableCellRenderer {
	public CountRenderer(int max) {
	    // range is 0..max
	    super(0, max);

	    // on win32, this forces native pixel-granularity
	    // progressbars -- otherwise they'd be chunky
	    setStringPainted(true);
	    setString("");
	}
	public Component getTableCellRendererComponent(JTable table,
						       Object value,
						       boolean isSelected, boolean hasFocus,
						       int row, int column) {
	    setValue(((Integer) value).intValue());
	    return this;
	}
    }

    public final class HistogramTableModel extends AbstractTableModel {
	// the histogram to display; its cross
	private Cross c;
	private Histogram h;
	private Histogram.Bucket b[];

	// formatters
	private DecimalFormat df; // scores
	private DecimalFormat pctFmt; // pct

	/** Create a Tablemodel to display the significant scores of a
	    given Cross.
	    @param c the Cross to be displayed */
	public HistogramTableModel(Cross c) {
	    // copy data
	    this.c = c;
	    h = new Histogram(c);
	    b = h.getBuckets();

	    df = new DecimalFormat(c.getFormat());
	    pctFmt = new DecimalFormat("0.0%");
	}

	public void formatChanged() {
	    df = new DecimalFormat(c.getFormat());
	}

	/** The column name.  The columns are:
	    <ul>
	    <li>Score range
	    <li>Number
	    <li>Percent
	    <li>Number (again, as a bar)
	    </ul>
	    @param col the column to query
	    @return the column's name */
	public String getColumnName(int col) {
	    switch (col) {
	    case 0: return c.getName();
	    case 1: return msg.getString("quantity");
	    case 2: return msg.getString("percent");
	    case 3: return msg.getString("histogram");
	    default: return null;
	    }
	}

	/** The number of rows.
	    @return the number of rows */
	public int getRowCount() {
	    return b.length;
	}

	/** The number of columns.
	    @return the number of columns */
	public int getColumnCount() {
	    return 4;
	}

	/** The class of a given column.  This is used so the default
	    table renderer right-aligns Integers.
	    @param col the column to query
	    @return Class of this column's data */
	public Class getColumnClass(int col) {
	    switch (col) {
	    case 0: return String.class;
	    case 1: return Integer.class;
	    case 2: return String.class;
	    case 3: return Integer.class;
	    default: return null;
	    }
	}

	/** The value at a (row, col) location.
	    @param row row number to query
	    @param col column number to query
	    @return the cell's value */
	public Object getValueAt(int row, int col) {
	    if (c == null) // is this test needed?
		return null;

	    switch (col) {

	    case 0: return df.format(b[row].low) + " - " + df.format(b[row].high);

	    case 1:
		if (b[row].number == 0)
		    return null;
	    case 3: // ... else ... (fall-through!)
		return new Integer(b[row].number);

	    case 2: return (b[row].number == 0 ? null : pctFmt.format(b[row].pct));

	    default: return null;

	    }
	}
    }

    // gui setup
    private void initGui() {
	// timer ------------------------------------------------------------
	timer = new Timer(10 /* ms */, new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
		    if (cross.isFinished()) {
			// stop timer
			((Timer) ae.getSource()).stop();

			// show tables
			if (scoresTable == null)
			    initTables();
			else
			    updateTables();

			// update titles
			fixedTitle.setText(cross.fixed.toString());
			movingTitle.setText(cross.moving.toString());
			fixedTree.setSample(cross.fixed);
			movingTree.setSample(cross.moving);
		    }
		}
	    });

	// bottom panel ------------------------------------------------------------
        JPanel buttons = new JPanel(new ButtonLayout());
        getContentPane().add(buttons, BorderLayout.SOUTH);

	// prev
	prevButton = new JButton(msg.getString("prev"),
				 JarIcon.getJavaIcon("toolbarButtonGraphics/navigation/Back16.gif"));
	prevButton.setMnemonic(msg.getString("prev_key").charAt(0));
	prevButton.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent ae) {
		    // disable buttons during processing
		    disableButtons();

		    // get prev cross
		    if (!seq.isFirst()) {
			seq.prevPairing();
			cross = seq.getCross();
		    }

		    // run it in its own thread
		    Thread thread = new Thread(cross);
		    timer.start();
		    thread.start();
		}
	    });
	buttons.add(prevButton);

	// next
	nextButton = new JButton(msg.getString("next"),
				 JarIcon.getJavaIcon("toolbarButtonGraphics/navigation/Forward16.gif"));
	nextButton.setMnemonic(msg.getString("next_key").charAt(0));
	nextButton.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent ae) {
		    // disable buttons during processing
		    disableButtons();

		    // get next cross
		    if (!seq.isLast()) {
			seq.nextPairing();
			cross = seq.getCross();
		    }

		    // run it in its own thread
		    Thread thread = new Thread(cross);
		    timer.start();
		    thread.start();
		}
	    });
	buttons.add(nextButton);

	// graph
	graphButton = new JButton(msg.getString("plot"));
	graphButton.setMnemonic(msg.getString("plot_key").charAt(0));
	graphButton.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent ae) {
		    // "significant scores" selected
		    if (tabPane.getSelectedIndex() == 0) {
			int nr = sigsTable.getSelectedRow(); // which cross to use

			// error-checking! -- make sure user picked something
			if (nr == -1) {
			    JOptionPane.showMessageDialog(null,
							  msg.getString("selecterror"),
							  msg.getString("error"),
							  JOptionPane.ERROR_MESSAGE);
			    return;
			}

			// get year (== moving sample end-year) for this row
			Year y = ((Score) cross.highScores.get(nr)).movingRange.getEnd();

			// new cross at this offset
			new GraphFrame(cross, y);

		    // "all scores" selected
		    } else {
			int r = scoresTable.getSelectedRow();
			int c = scoresTable.getSelectedColumn();
			if (r==-1 || c==-1) {
			    JOptionPane.showMessageDialog(null,
							  msg.getString("selecterror"),
							  msg.getString("error"),
							  JOptionPane.ERROR_MESSAGE);
			    return;
			}
			Year y = ((CrossTableModel) scoresTable.getModel()).getYear(r, c);
			new GraphFrame(cross, y);
		    }
		}
	    });
	buttons.add(graphButton);

	// map (!)
	mapButton = new JButton(msg.getString("map"));
	mapButton.setMnemonic(msg.getString("map_key").charAt(0));
	mapButton.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent ae) {
		    // 1-by-N crosses are special
		    /* -- need a new way to figure out if the're special now, sucka
		    if (seq instanceof Sequence1xN) {
			Sequence1xN seq1n = (Sequence1xN) seq;
			Site s1, s2[];
			try {
			    s1 = SiteDB.getSiteDB().getSite(seq1n.f);
			    s2 = new Site[seq1n.m.size()];
			    for (int i=0; i<seq1n.m.size(); i++)
				s2[i] = SiteDB.getSiteDB().getSite(((Element) seq1n.m.get(i)).load()); // wasteful!
			    new MapFrame(s1, s2);
			} catch (IOException ioe) {
			    System.out.println("ioe = " + ioe.getMessage());
			} catch (Exception e) {
			    System.out.println("e = " + e.getMessage());
			}

			return;
		    }
		    */

		    // figure out the locations
		    Site s1, s2;
		    try {
			s1 = SiteDB.getSiteDB().getSite(cross.fixed);
			s2 = SiteDB.getSiteDB().getSite(cross.moving);
		    } catch (Exception e) {
			JOptionPane.showMessageDialog(null,
						      msg.getString("maperror"),
						      msg.getString("error"),
						      JOptionPane.ERROR_MESSAGE);
			return;
		    }

		    // draw the map
		    try {
			new MapFrame(s1, s2);
		    } catch (IOException ioe) {
			System.out.println("ioe! -- " + ioe);
		    }
		}
	    });
	buttons.add(mapButton);

	// close
	closeButton = new JButton(msg.getString("close"));
	closeButton.setMnemonic(msg.getString("close_key").charAt(0));
	closeButton.addActionListener(new AbstractAction() {
		public void actionPerformed(ActionEvent ae) {
		    dispose();
		}
	    });
	buttons.add(closeButton);

	// center panel ----------------------------------------------------------------------
	tabPane = new JTabbedPane();
	getContentPane().add(tabPane, BorderLayout.CENTER);

	// top panel ----------------------------------------------------------------------
	JPanel top = new JPanel();

	/* strategy:
	   [-->Fixed:] [@] [Zonguldak, Karabuk 3A]
	   [->Moving:] [@] [Zonguldak, Karabuk 4A]
	*/
	GridBagLayout gbl = new GridBagLayout();
	top.setLayout(gbl);
	GridBagConstraints con = new GridBagConstraints();
	con.insets = new Insets(0, 4, 0, 4);
	con.weightx = 0.0;
	con.fill = GridBagConstraints.HORIZONTAL;

	// "fixed", "moving" -- BUG: these aren't right-aligned, like i think they should be
	con.anchor = GridBagConstraints.EAST;
	con.gridx = 0;
	con.gridy = 0;
	JLabel f = new JLabel(msg.getString("fixed") + ":", SwingConstants.RIGHT);
	gbl.setConstraints(f, con);
	top.add(f);
	con.gridy = 1;
	JLabel m = new JLabel(msg.getString("moving") + ":", SwingConstants.RIGHT);
	gbl.setConstraints(m, con);
	top.add(m);

	// trees -- BUG: these don't get updated on next/prev
	con.gridx = 1;
	con.gridy = 0;
	con.anchor = GridBagConstraints.CENTER;
	fixedTree = new Tree(cross.fixed);
	gbl.setConstraints(fixedTree, con);
	top.add(fixedTree);
	con.gridy = 1;
	movingTree = new Tree(cross.moving);
	gbl.setConstraints(movingTree, con);
	top.add(movingTree);

	// titles -- BUG: these don't get updated on next/prev
	con.weightx = 1.0;
	con.gridx = 2;
	con.gridy = 0;
	con.anchor = GridBagConstraints.WEST;
	fixedTitle = new JLabel(cross.fixed.toString());
	gbl.setConstraints(fixedTitle, con);
	top.add(fixedTitle);
	con.gridy = 1;
	movingTitle = new JLabel(cross.moving.toString());
	gbl.setConstraints(movingTitle, con);
	top.add(movingTitle);
	
	// TODO: add "swap f/m" button to right side, 1-line high,
	// align-right, centered vertically in the 2-line space (its
	// own panel, somehow)

	getContentPane().add(top, BorderLayout.NORTH);
    }

    /** Update both tables, the frame title, and the prev/next
        buttons. */
    private void updateTables() {
	// tables
	scoresTable.setModel(new CrossTableModel(cross));
	sigsTable.setModel(new CrossSigsTableModel(cross));
	sigsTable.getColumnModel().getColumn(0).setPreferredWidth(24); // number column is too big
	sigsTable.getColumnModel().getColumn(0).setMaxWidth(36);
	histogram.setModel(new HistogramTableModel(cross));
	histogram.getColumnModel().getColumn(3).setCellRenderer(new CountRenderer(cross.data.length));
	// => max(buckets[].count) would be better

	// select highest score
	selectHighest();

	// hilite sig scores
	// scoresTable.setDefaultRenderer(Double.class, new ScoreRenderer(cross)); -- ???
	for (int i=1; i<=10; i++)
	    scoresTable.getColumnModel().getColumn(i).setCellRenderer(new ScoreRenderer(cross));

	// title
	setTitle(cross.toString());

	// buttons
	prevButton.setEnabled(!(seq.isFirst()));
	nextButton.setEnabled(!(seq.isLast()));
	graphButton.setEnabled(true);
    }

    private class ScoreRenderer extends JLabel implements TableCellRenderer {
        private DecimalFormat df;
        public ScoreRenderer(Cross c) {
            super();
            setHorizontalTextPosition(SwingConstants.RIGHT); // FIXME: isn't this ignored?
            setOpaque(true);
            df = new DecimalFormat(c.getFormat());
        }
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            // always reset fore,background -- check for nulls?
            super.setBackground(Color.getColor("corina.cross.background"));
            super.setForeground(Color.getColor("corina.cross.foreground"));

            // null: bail
            if (value == null) {
                setText("");
                return this;
            }

            // font -- needed here?
            super.setFont(table.getFont());

            // score
            double score =((Number) value).doubleValue();

            // [- change: value=Double, and do formatting here!] -- this comment is old, but might be useful still
            setText(df.format(score));

            // hilite sig scores; BINARY ONLY: USE ANALOG HERE, IF DESIRED
            if (score > cross.getMinimumSignificant())
                super.setBackground(Color.getColor("corina.grid.highlightcolor"));

            return this;
        }
    }

    // select the highest score in the sigs table
    private void selectHighest() {
        double high = Double.NEGATIVE_INFINITY;
        for (int i=0; i<cross.highScores.size(); i++) {
            double test = ((Score) cross.highScores.get(i)).score;
            if (test > high) {
                sigsTable.setRowSelectionInterval(i, i);
                high = test;
            }
        }
    }

    /** Initialize both tables, the frame title, and the prev/next
        buttons. */
    private void initTables() {
	// scores
	scoresTable = new JTable(new CrossTableModel(cross));
	scoresTable.setRowSelectionAllowed(false);
	scoresTable.getTableHeader().setReorderingAllowed(false);

	// testing: hilite sig scores
	// scoresTable.setDefaultRenderer(Double.class, new ScoreRenderer(cross));
	for (int i=1; i<=10; i++)
	    scoresTable.getColumnModel().getColumn(i).setCellRenderer(new ScoreRenderer(cross));

	// double-click-able
	scoresTable.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
		    // respond only to double-clicks
		    if (e.getClickCount() != 2)
			return;

		    // get the (row,col) of the click
		    int row = scoresTable.rowAtPoint(e.getPoint());
		    int col = scoresTable.columnAtPoint(e.getPoint());

		    // get the year (== end-date of moving sample)
		    Year y = ((CrossTableModel) scoresTable.getModel()).getYear(row, col);

		    // new graph at this place
		    new GraphFrame(cross, y);
		}
	    });

	// sigs
	sigsTable = new JTable(new CrossSigsTableModel(cross));
	sigsTable.getTableHeader().setReorderingAllowed(false);
	sigsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	sigsTable.getColumnModel().getColumn(0).setPreferredWidth(24); // number column is too big
	sigsTable.getColumnModel().getColumn(0).setMaxWidth(36);

	selectHighest();

	// double-click-able
	sigsTable.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
		    // respond only to double-clicks
		    if (e.getClickCount() != 2)
			return;

		    // get the row of the click
		    int row = scoresTable.rowAtPoint(e.getPoint());

		    // get the year (== end-date of moving sample)
		    Year y = ((Score) cross.highScores.get(row)).movingRange.getEnd();

		    // new cross at this offset
		    new GraphFrame(cross, y);
		}
	    });

	// sortable -- fixme: maintain selected row
	final JTable glue = sigsTable;
	sigsTable.getTableHeader().addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
		    int col = glue.getColumnModel().getColumnIndexAtX(e.getX());
		    if (e.getClickCount() == 1) {
			switch (col) {
			case 0: // number
			    Collections.sort(cross.highScores, new Comparator() {
				    public int compare(Object o1, Object o2) {
					int s1 = ((Score) o1).number;
					int s2 = ((Score) o2).number;
					return (s1 - s2);
				    }
				});
			    ((AbstractTableModel) glue.getModel()).fireTableDataChanged(); // is this needed?
			    break;
			case 1: // fixed range
			    // sort by fixedRange?
			    break;
			case 2: // moving range
			    // sort by movingRange?
			    break;
			case 3: // score
			    Collections.sort(cross.highScores, new Comparator() {
				    public int compare(Object o1, Object o2) {
					double s1 = ((Score) o1).score;
					double s2 = ((Score) o2).score;
					return (s1 > s2 ? -1 : 1); // no zero possible?
				    }
				});
			    ((AbstractTableModel) glue.getModel()).fireTableDataChanged();
			    break;
			case 4: // overlap
			    Collections.sort(cross.highScores, new Comparator() {
				    public int compare(Object o1, Object o2) {
					int s1 = ((Score) o1).span;
					int s2 = ((Score) o2).span;
					return (s2 - s1);
				    }
				});
			    ((AbstractTableModel) glue.getModel()).fireTableDataChanged();
			    break;
			}
		    }
		}
	    });

	// histogram
	histogram = new JTable(new HistogramTableModel(cross));
	histogram.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	histogram.getColumnModel().getColumn(3).setCellRenderer(new CountRenderer(cross.data.length));
	// => max(buckets[].count) would be better

	// set preference-able stuff
	refreshFromPreferences();

	// stuff tables into tabs
	tabPane.addTab(msg.getString("sig_scores"), new JScrollPane(sigsTable));
	tabPane.addTab(msg.getString("all_scores"), new JScrollPane(scoresTable));
	tabPane.addTab(msg.getString("score_distro"), new JScrollPane(histogram));

	// label
	setTitle(cross.toString());

	// buttons
	prevButton.setEnabled(!(seq.isFirst()));
	nextButton.setEnabled(!(seq.isLast()));
	graphButton.setEnabled(true);
    }

    private void disableButtons() {
	prevButton.setEnabled(false);
	nextButton.setEnabled(false);
	graphButton.setEnabled(false);
    }

    /** Create a crossdate for a given Sequence.
	@param s the Sequence to display */
    public CrossFrame(Sequence s) {
	// copy data
	seq = s;
	cross = seq.getCross();

	// init gui (incl. timer)
	initGui();

	// menubar: in testing
	setJMenuBar(new XMenubar(this, null));

	// run cross
	Thread thread = new Thread(cross);
	disableButtons();
	timer.start();
	thread.start();

	// show
	pack();
	setSize(new Dimension(640, 480));
	show();
    }

    public void refreshFromPreferences() {
	// gridlines
	boolean gridlines = Boolean.getBoolean("corina.cross.gridlines");
	scoresTable.setShowGrid(gridlines);
	sigsTable.setShowGrid(gridlines);

	// format strings
	for (int i=1; i<=10; i++) // format strings updated
	    scoresTable.getColumnModel().getColumn(i).setCellRenderer(new ScoreRenderer(cross));
	((CrossSigsTableModel) sigsTable.getModel()).formatChanged();

	// font
	if (System.getProperty("corina.cross.font") != null) {
	    Font f = Font.getFont("corina.cross.font");
	    scoresTable.setFont(f);
	    sigsTable.setFont(f);
	    histogram.setFont(f);
	    scoresTable.setRowHeight(f.getSize() + 3);
	    sigsTable.setRowHeight(f.getSize() + 3);
	    histogram.setRowHeight(f.getSize() + 3);
	}

	// colors
	if (System.getProperty("corina.cross.background") != null) {
	    scoresTable.setBackground(Color.getColor("corina.cross.background"));
	    sigsTable.setBackground(Color.getColor("corina.cross.background"));
	}
	if (System.getProperty("corina.cross.foreground") != null) {
	    scoresTable.setForeground(Color.getColor("corina.cross.foreground"));
	    sigsTable.setForeground(Color.getColor("corina.cross.foreground"));
	}
    }

    // printing
    public Printable print(PageFormat pf) {
	return new CrossPrinter(cross);
    }
}
