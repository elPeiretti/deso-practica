# Compila las diapositivas con XeLaTeX y el tema de resources/.
# El PDF queda junto al .tex; los archivos auxiliares van a resources/build/.
$pdf_mode = 5;
$xelatex = 'xelatex -interaction=nonstopmode -file-line-error %O %S';
$aux_dir = 'resources/build';
$out_dir = '.';
$ENV{'TEXINPUTS'} = 'resources//:' . ($ENV{'TEXINPUTS'} // '');
