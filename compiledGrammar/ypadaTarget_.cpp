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
// class factorial { 
int main() {
bool yipadaFree_0 ; 
int yipadaFree_1 ; 
int yipadaFree_2 ; 
int fact ; 
int N ; 
fact=1;
cin>>N;
cout<<N<<"\n";
_ypda_label_9:
_reInitialiseComparisonFlags();
if (N<0)
_b_JAE = 0 ; // not >=
if (N <= 0)
_b_JA = 0 ;  // not >
if (N > 0)
_b_JBE = 0 ;  // not <=
if (N >= 0)
_b_JB = 0 ;  // not <
if (N == 0)
_b_JNE = 0 ;  // not !=
if (N != 0)
_b_JE = 0 ;  // not ==
 if (_b_JBE == 1) goto _ypda_label_11 ;
yipadaFree_0=1;
goto _ypda_label_12 ;
_ypda_label_11:
yipadaFree_0=0;
_ypda_label_12:
_reInitialiseComparisonFlags();
if (yipadaFree_0<0)
_b_JAE = 0 ; // not >=
if (yipadaFree_0 <= 0)
_b_JA = 0 ;  // not >
if (yipadaFree_0 > 0)
_b_JBE = 0 ;  // not <=
if (yipadaFree_0 >= 0)
_b_JB = 0 ;  // not <
if (yipadaFree_0 == 0)
_b_JNE = 0 ;  // not !=
if (yipadaFree_0 != 0)
_b_JE = 0 ;  // not ==
 if (_b_JE == 1) goto _ypda_label_10 ;
yipadaFree_1=fact*N;
fact=yipadaFree_1;
yipadaFree_2=N-1;
N=yipadaFree_2;
goto _ypda_label_9 ;
_ypda_label_10:
cout<<" Fact ="<<"\n";
cout<<fact<<"\n";
return 0 ; 
}  // end method main
//   }  // end class factorial
