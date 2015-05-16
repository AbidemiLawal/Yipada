#include <iostream>
#include <stdlib.h>
using namespace std ;
bool _b_JMP_ZERO  ;
bool _b_JMP_POSITIVE ;
bool _b_JMP_NEGATIVE ; 
bool _b_JA ; 
bool _b_JAE ; 
bool _b_JB ; 
bool _b_JBE ; 
bool _b_JE ; 
bool _b_JNE ; 
void _reInitialiseComparisonFlags(){
 _b_JMP_ZERO = 1 ;
 _b_JMP_POSITIVE = 1 ;
 _b_JMP_NEGATIVE = 1 ;
 _b_JA = 1 ;
 _b_JAE = 1 ;
 _b_JB = 1 ;
 _b_JBE = 1 ;
 _b_JE = 1 ;
 _b_JNE = 1 ;
}
// class Hello { 
int main() {
cout<<"hello "<<"\n";
return 0 ; 
}  // end method main
//   }  // end class Hello
// class Man { 
float test() {
cout<<"hello "<<"\n";
return 0.0 ; 
}  // end method test
//   }  // end class Man
