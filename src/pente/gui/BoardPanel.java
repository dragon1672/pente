package pente.gui;

import pente.board.*;

import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;


/**
 * This component assumes a SQUARE BOARD for drawing math. For now, assuming the board is always 19x19, but will eventually
 * update this to be based on the underlying Board object.
 *
 * Created by kstimson on 12/30/2016.
 */
public class BoardPanel extends JPanel{
    private static final Color BROWN = new Color(100, 39, 17);
    private static final Color LIGHTER_BROWN = new Color(142, 38, 0);
    private Board state;

    public BoardPanel(Board state) {
        if(state == null) throw new IllegalArgumentException("state cannot be null");
        this.state = state;
    }

    @Override
    public void paintComponent(Graphics g) {
        drawBackground(g);
        drawOverlays(g);
        drawPieces(g);
    }

    private void drawBackground (Graphics g) {
        int cellLength, cellOffset, numCells = 19, boardLength;
        cellLength = Math.min(getWidth()/(numCells), getHeight()/(numCells));
        cellOffset = cellLength/2;
        boardLength = cellLength*(numCells);

        //draw the base background
        g.setColor(LIGHTER_BROWN);
        g.fillRect(0, 0, boardLength, boardLength);

        //prepare for drawing the lines
        g.setColor(BROWN);

        //start drawing the lines
        int startPosition = cellOffset, endPosition = cellOffset + ((numCells-1) * cellLength);
        for (int i = 0; i < numCells; i++) {
            int position = cellOffset + (i*cellLength);

            //draw horizontal
            g.drawLine(startPosition, position, endPosition, position);

            //draw vertical
            g.drawLine(position, startPosition, position, endPosition);
        }
    }

    private void drawPieces(Graphics g) {
        int cellLength, numCells = 19;
        cellLength = Math.min(getWidth()/(numCells), getHeight()/(numCells));

        if(state != null) {
            state.getPieceLocations().forEach(
                vector -> {
                    g.setColor(state.getColor(vector).toRGBColor());
                    g.fillOval(vector.getX()*cellLength, vector.getY()*cellLength, cellLength, cellLength);
                }
            );
        }
    }

    private void drawOverlays(Graphics g) {
        drawChains(g);
        drawCapturePoints(g);
        drawThreatenedPoints(g);
    }

    private void drawChains(Graphics g) {

    }

    private void drawCapturePoints(Graphics g) {

    }

    private void drawThreatenedPoints (Graphics g) {

    }
}