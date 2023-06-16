
package  {
	import flash.display.MovieClip;
	import flash.events.Event;
	import flash.events.KeyboardEvent;

 //aqui adentro estan los import, estos son los paquetes
	
	public class protagonista extends MovieClip { //Clase
	       private var izq: Boolean = false;
		   private var der: Boolean = false;
		   private var vel: int = 10;
		   private var lim_x_der: int = 510;
		   private var lim_x_izq: int = 40;
		   
		   //Los metodos pueden ser publicos y privados
	
	//variables de clase

		public function protagonista() {
			// constructor code
			addEventListener( Event.ENTER_FRAME, motor );
//Identacion-----
  			
		}
		public function motor(e: Event):void{
			if( der ){
				if(x <= lim_x_der){
					x+=vel;
				}
			} else if( izq ){
				if( x >= lim_x_izq){
					x-=vel;
				}
				
			}
			controles();
		}
		public function controles(){  //Key down cuando la tecla se hunde y Key up se eleva
			stage.addEventListener( KeyboardEvent.KEY_DOWN, keydown);
			stage.addEventListener( KeyboardEvent.KEY_UP, keyup);
			
		}
		public function keydown(e: KeyboardEvent):void{
			switch (e.keyCode){
				case 39: //ASCII 68=d 65=a
					der = true;
					break;
				
				case 37:
					izq = true;
					break;
			}
			
		}
		public function keyup(e: KeyboardEvent):void{
			switch(e.keyCode){
				case 39:
					der = false;
					break;
					
			 	case 37:
					izq = false;
					break;
			}
			
		}

	}
}	

