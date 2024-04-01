package dev.stormy.client.clickgui;

public interface Component {
   void draw();

   void update(int mousePosX, int mousePosY);

   void mouseDown(int x, int y, int button);

   void mouseReleased(int x, int y, int button);

   void keyTyped(char typedChar, int keyCode);

   void setComponentStartAt(int position);

   int getHeight();
}
