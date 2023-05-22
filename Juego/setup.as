package  {
	
	import flash.display.MovieClip;
	
	
	public class setup extends MovieClip {
		
			private var fondo_new: fondo = new fondo();		
			private var piso_new: piso = new piso();
			private var protagonista_new: protagonista = new protagonista();
			private var antagonista_new: antagonista = new antagonista();
			private var bala_new: bala = new bala();
			
		public function setup() {
			// constructor code
			
			addChild( fondo_new );
			addChild( piso_new );
			addChild( protagonista_new );
			addChild( antagonista_new );
			
			fondo_new.y = 0;
			fondo_new.x = 0;
			
			piso_new.y = 355;
			piso_new.x = 0;
			
			protagonista_new.y = 250;
			protagonista_new.x = 275;
			
			antagonista_new.y = 50;
			antagonista_new.x = 275;
		}
	}
	
}
